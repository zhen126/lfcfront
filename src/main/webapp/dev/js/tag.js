        Mock.mock("http://localhost:8080/lfcfront/tagList",{
        	 "totalCount":1000,
			 "tagList|20":[{"tagName": "@word(7)",
			 "tagId|198-218": 20}]
			
		});
		$.ajax({
				type:"get",
				url:"http://localhost:8080/lfcfront/tagList",
				async:true,
				dataType: 'json',
				data:{},
				success:function(data){
					 $(".news_right_list dl").html("");
					 var value = data.tagList;
					 for (var i = 0; i<value.length; i++) {
                      //动态生成a标签，根据接口文档的需要
						var oEle = $("<dd><a href='#"+value[i].tagId+"'>"+value[i].tagName+"</a><dd>");
						 $(".news_right_list dl ").append(oEle);	
					 }
				 }
			});