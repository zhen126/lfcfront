//mock模拟数据
Mock.mock("http://localhost:8080/lfcfront/postDetail",{
	"postId|1":[
						{
							"title":"陆鹰前端社区文章标题",
							"author":"王飞飞",
							"time":"2016年12月01日 18:00",
							"content":"本站的各项电子服务的所有权和运作权归本站。本站提供的服务将完全按照其发布的服务条款和操作规则严格执	行。您同意所有本站发布的服务条款和操作规则的约定.",
							comments:{
								"commentsList":{
									"id":"112",
									"comment":"今天星期五,这里是评论内容,今天星期五,这里是评论内容.因此我们就会看到很多开源软件其实都成了“版本帝”。",
									"c_author":"王飞飞",
									"c_time":"2016年12月02日 18:00",
									"postId":"110"
								}
							}
						}
					]
});

//文章详情部分
$(document).ready(function(){
	//Ajax获取mock模拟数据
	var param = {
		postId:192
	}
	$.ajax({
		type:"post",
		url:"http://localhost:8080/lfcfront/postDetail",
		data:param,
		async:true,
		dataType:"json",
		success:function(res){
			if(res){
				var arr = res.postId;
				var commentList  = arr.comments.commentsList;
				var arclist = $(".arc_list");
				var author = $(".author_info");
				//文章详情
				arclist.find("h1").html(arr.title);//标题
				arclist.find("span a").html(arr.author);//作者
				arclist.find("span i").html(arr.time);//发布时间
				arclist.find("p").html(arr.content);//文章内容
				//评论详情
				author.find("span a").html(commentList.c_author);//作者
				author.find("p").html(commentList.comment);//评论内容
			}else{
				alert("没有相关信息");
			}
		}
	});
});
