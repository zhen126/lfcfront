Mock.mock("http://localhost:8080/lfcfront/myPost", {
	"totalCount": 1000,
	"postList": [{
		"postId": 198,
		"title": "文章标题",
		"tag": "js框架,Angular",
		"content": "文章内容",
		"author": "张三",
		"time": "2015年12月21日 18:36"
	}, {
		"postId": 198,
		"title": "文章标题",
		"tag": "js框架,Angular",
		"content": "文章内容",
		"author": "张三",
		"time": "2015年12月21日 18:36"
	}, {
		"postId": 198,
		"title": "文章标题",
		"tag": "js框架,Angular",
		"content": "文章内容",
		"author": "张三",
		"time": "2015年12月21日 18:36"
	}, {
		"postId": 198,
		"title": "文章标题",
		"tag": "js框架,Angular",
		"content": "文章内容",
		"author": "张三",
		"time": "2015年12月21日 18:36"
	}, {
		"postId": 198,
		"title": "文章标题",
		"tag": "js框架,Angular",
		"content": "文章内容",
		"author": "张三",
		"time": "2015年12月21日 18:36"
	}, {
		"postId": 198,
		"title": "文章标题",
		"tag": "js框架,Angular",
		"content": "文章内容",
		"author": "张三",
		"time": "2015年12月21日 18:36"
	}, {
		"postId": 198,
		"title": "文章标题",
		"tag": "js框架,Angular",
		"content": "文章内容",
		"author": "张三",
		"time": "2015年12月21日 18:36"
	}, {
		"postId": 198,
		"title": "文章标题",
		"tag": "js框架,Angular",
		"content": "文章内容",
		"author": "张三",
		"time": "2015年12月21日 18:36"
	}, {
		"postId": 198,
		"title": "文章标题",
		"tag": "js框架,Angular",
		"content": "文章内容",
		"author": "张三",
		"time": "2015年12月21日 18:36"
	}]

});

$.ajax({
	type: "post",
	url: "http://localhost:8080/lfcfront/myPost",
	async: true,
	success: function(data) {
		if(data)
			var myPosts = JSON.parse(data);
		var postsList = myPosts.postList;
		$.each(postsList, function(k, value) {
			var art = "";
			art += '<div class="arc_list"><h1><a href="arc_info.html" title=""class="posts_title">' + value.title + '</a></h1><span>作者：<a href="#" class="posts_author">' + value.author + '</a>   时间：<a class="posts_time">' + value.time + '</a></span><div></div><p class="posts_content">' + value.content + '</p><button><a href="arc_info.html">查看更多</a></button></div>';
			$(".pagelist").before(art);
			$(".posts_author").html(value.author);
			$(".posts_time").html(value.time);
			$(".posts_title").html(value.title);
			$(".posts_content").html(value.content);			
		});
	}
});
