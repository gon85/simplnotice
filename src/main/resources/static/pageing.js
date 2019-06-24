/**
 * 
 */
	Vue.component('my-pageing', {
		data : function () {
			return {
				nextPageCnt : 10
				,prePageCnt : 10
			}
		}
		,props : [
			'totalRow'
			,'rowPerPage'
			,'currentPage'
			,'pageNoPerPage'
		]
		,props : {
			totalRow : {
				type : Number
				,default : 0
			}
			,rowPerPage : {
				type : Number
				,default : 10
			}
			,currentPage : {
				type : Number
				,default : 1
			}
			,pageNoPerPage : {
				type : Number
				,default : 10
			}
		}
		,template: '#pageNavi'
		,computed:{
			pageNums : function () {
				//var start = 11; end = 20;
				var totalPage = parseInt( (this.totalRow - 1) / this.rowPerPage ) + 1;
				//var start = parseInt( (this.currentPage - 1) / this.rowPerPage ) * this.rowPerPage + 1;
				var start = parseInt( (this.currentPage - 1) / this.pageNoPerPage ) * this.pageNoPerPage + 1;
				var end =  start + this.pageNoPerPage - 1;
				if (totalPage < end) {
					end = totalPage;
					this.nextPageCnt = 0;
				} else {
					this.nextPageCnt = (totalPage - end > this.pageNoPerPage ? this.pageNoPerPage : totalPage - end);
				}
				this.prePageCnt = (start > 1 ? this.pageNoPerPage : 0);
				var nums = [];
				for (var i = start; i <= end; i++) {
					nums.push(i);
				}
				return nums;
				
			}
		},
		
		methods : {
			setPage : function (page) {
				this.$emit('changepage', page);
			}
		}
	});	
