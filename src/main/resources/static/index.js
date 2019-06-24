/**
 * 
 */
Vue.prototype.gdateFormat = function (val) {
	if (val == null)
		return "";
	
	return moment(val).format('YYYY-MM-DD');
};

const store = new Vuex.Store({
	state : {
		noticeList : [],
		noticeListCount : 0,
		pageInfo : {
			totalRow : 0
			,rowPerPage : 10
			,currentPage : 1
			,pageNoPerPage : 10
		},
		notice : {}
	},
  mutations: {
		setNoticeList : function(state, payload) {
			state.noticeList = payload.list;
			state.noticeListCount = payload.count;
			state.pageInfo.totalRow = payload.count;
			// state.pageInfo = payload.pageInfo;
		},
		setNotice : function (state, payload) {
			state.notice = payload.notice;
		},
  },
	actions : {
		getNotices : function (context, payload) {
			var that = this;
			return new Promise((resolve, reject) => {
				axios.post("/notice/list", payload.params)
				.then(function (res) {
					var payload = {
							list : res.data.results.list.list,
							count : res.data.results.list.totalCount
					};
					//payload = res.results.list;
					context.commit('setNoticeList', payload);
					resolve();
				}).catch(function (error) {
					console.log("---->" + error);
					reject();
				});
			});
		},
		getNewNotice : function (context, payload) {
			var that = this;
			return new Promise((resolve, reject) => {
				axios.get("/notice/new", payload)
				.then(function (res) {
					var payload = {};
					payload.notice = res.data.results.notice;
					context.commit('setNotice', payload);
					resolve();
				}).catch(function (error) {
					console.log("---->" + error);
					reject();
				});
			});
		},
		saveNotice : function (context, payload) {
			var that = this;
			that.state.notice.contentType = payload.type;
			return new Promise((resolve, reject) => {
				axios.post("/notice/save", that.state.notice, {headers:{'content-type':'application/json'}})
				.then(function (res) {
					resolve(res);
				}).catch(function (error) {
					console.log("---->" + error);
					reject();
				});
			});
		},
		countViewNotice : function (context, payload) {
			var that = this;
			that.state.notice.contentType = payload.type;
			return new Promise((resolve, reject) => {
				var param = "idx=" + payload.idx;
				axios.post("/notice/viewcount", param)
				.then(function (res) {
					resolve(res);
				}).catch(function (error) {
					console.log("---->" + error);
					reject();
				});
			});
		},
		removeNotice : function (context, payload) {
			var that = this;
			return new Promise((resolve, reject) => {
				var param = "idx=" + payload.idx + "&userPw=" + payload.userPw;
				axios.post("/notice/remove", param)
				.then(function (res) {
					resolve(res);
				}).catch(function (error) {
					console.log("---->" + error);
					reject();
				});
			});
		},
		confirmPw : function (context, payload) {
			var that = this;
			return new Promise((resolve, reject) => {
				axios.post("/notice/confirmPw", payload.params)
				.then(function (res) {
					resolve(res);
				}).catch(function (error) {
					console.log("---->" + error);
					reject();
				});
			});
		},
  }
})

var noticeListVue = {
	template : '#noticeListVue',
	store,
	data : function() {
		return {
		};
	},
	computed : {
		pageInfo : function () {
				return this.$store.state.pageInfo;
		}
	},
	methods : {
		ready : function () {
			var that = this;
			that.$store.state.notice = {};
			that.getNotices();
		},
		getNotices : function (page) {
			var that = this;
			if (page) {
				that.pageInfo.currentPage = page;
			}

			this.$nextTick(function () {
				var dataParam = $('#frm').serialize();
				that.$store.dispatch("getNotices", { params : dataParam })
				.then(function () {
					
				})
				.catch(function (e) {
					alert("서버오류");
				});
			});
		},
		changePageByPageing : function (page) {
			var that = this;
			that.getNotices(page);
		},
		changeConditions : function() {
			var that = this;
			that.getNotices(page);
		}
	}
	,mounted : function () {
		this.ready();
	}
}

var noticeDetailVue = {
	template : '#noticeDetailVue',
	store,
	data : function() {
		return {
			popView : "",
			popViewFor : ""
		};
	},
	computed : {
		notice : function () {
			return this.$store.state.notice;
		}
	},
	methods : {
		ready : function () {
			//console.log("ready!!!");
			var that = this;
			
			if (that.$route.params && that.$route.params.idx) {
				var index = that.$store.state.noticeList.findIndex(function (element) {
					if (element.idx == that.$route.params.idx) {
						return true;
					} else {
						return false;
					}
				});

				that.$store.state.notice = JSON.parse(JSON.stringify(that.$store.state.noticeList[index]));
				that.$store.dispatch("countViewNotice", { idx : that.notice.idx})
				.then(function (res) {
					that.$store.state.notice.viewCnt = res.data.results.viewcount
				})
				.catch(function (e) {
					console.log(e);
				});
			} else {
				that.$store.dispatch("getNewNotice", { })
				.then(function () {
				})
				.catch(function (e) {
					console.log(e);
					alert("서버 오류 발생!!!");
				});
			}
			
			var sHTML = that.notice.contents;
		},
		saveNotice : function () {
			var that = this;

			if (this.validate() == false) {
				return;
			}
			
			var saveTF = confirm("저장 하시겠습니까?");
			if (saveTF) {
				if (that.notice.idx > 0)
					that.openPopupConfirmPw("S");
				else {
					that.saveNoticeInSever();
				}
			}
			
		},
		saveNoticeInSever : function() {
			var that = this;
			this.$store.dispatch("saveNotice", {})
			.then(function (res) {
				if (res.data.code == "OK") {
					alert("저장 되었습니다.");
					that.$router.push({ name: 'noticeListVue', params: { refresh: 'Y' }});
				} else {
					alert("저장 실패 (서버오류 발생)");
				}
			})
			.catch(function (e) {
				alert("서버 오류 발생!!!");				
			});
		},
		validate : function () {
			if (this.validateRequireString("제목은", this.notice.title) == false) {
				return false;
			}
			if (this.validateRequireString("내용은", this.notice.contents) == false) {
				return false;
			}
			if (this.validateRequireString("이용자 닉네임은", this.notice.userNickName) == false) {
				return false;
			}
		},
		validateRequireString : function (label, val) {
			if (val == null || val == "") {
				alert(label + " 필수 값 입니다.");
				return false;
			}
			return true;
		},
		callfunc : function (args) {
			var that = this;
			if (args.method == "confirmpw") {
				that.confirmpw(args.ok, args.pw);
			}
		},
		closePopView : function() {
				this.popView = "";
		},
		openPopupConfirmPw : function (popViewFor) {
			this.popViewFor = popViewFor;
			this.popView = "notice-confirm";
		},
		confirmpw : function (ok, pw) {
			var that = this;
			if (ok === "OK") {
				if (that.popViewFor === "R") {
					that.$store.dispatch("removeNotice", { idx : that.notice.idx, userPw : pw })
					.then(function () {
						alert("삭제 성공")
						that.$router.push({ name: 'noticeListVue', params: { refresh: 'Y' }});
					})
					.catch(function (e) {
						alert("서버 오류 발생!!!");
					});
				} else {
					that.notice.userPw = pw;
					that.saveNoticeInSever();
				}
			}
		},
		removeNotice : function() {
			var that = this;
			
			var removeTF = confirm("삭제 하시겠습니까?");
			if(removeTF == true){
				that.openPopupConfirmPw("R");
			}
		}
		
	},
	mounted : function () {
		var that = this;
		this.ready();
	}
}

var confirmPwVue = {
	template : '#confirmPwVue',
	store,
	data : function () {
		return {
			pw : ''
		};
	},
	computed : {
		notice : function () {
			return this.$store.state.notice;
		}
	},
	mounted : function () {
	},
	methods : {
		confirm : function () {
			// console.log("~~~~~~~~~~~~~~~~~~~~~~~~~" + this.$store.state.checkedTestUserSeqs.length);
			var that = this;
			var dataParam = $('#frmConfirm').serialize();
			this.$store.dispatch("confirmPw", { params : dataParam })
			.then(function (res) {
				if (res.data.code == "OK") {
					that.$emit('callfunc', {
						method : "confirmpw",
						ok : "OK",
						pw : $('#userPw').val()
					});
				} else {
					alert("비밀번호 불일치 변경 할 수 없습니다.");
				}
			})
			.catch(function (e) {
				alert("서버오류");
			});
			
		},
		close : function () {
			this.$emit('close');
		}
	}
}


Vue.component('notice-list', noticeListVue);
Vue.component('notice-detail', noticeDetailVue);
Vue.component('notice-confirm', confirmPwVue);

var routes = [
	{ path : '/', redirect : 'noticeListVue'}
	,{ path : '/noticeListVue', name : 'noticeListVue', component : noticeListVue }
	,{ path : '*', redirect :'noticeListVue' }
	,{ path : '/noticeDetailVue', name : 'noticeDetailVue', component : noticeDetailVue }
];
var router = new VueRouter({
	routes : routes
});

var app = new Vue({
	el:'#app',
	router : router
}).$mount('#app');
