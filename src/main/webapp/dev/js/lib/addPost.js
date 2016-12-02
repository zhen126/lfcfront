$(function(){
		finalTagTxt="";
		// 导航跟随固定
		offs=$('#fixed').offset();
		$(window).scroll(function(){
			scrollLis();
		});
		      
			tagValues="";
			// 标签颜色
			var tagsBg=[0,1,2,3,4,5,6];
			//添加标签
			$(document).on("click","#addTagBtn",function(){
                var $txtTag=$("#txtTag");
				tagValue=$txtTag.val();
				if(tagValue.trim()==""){
					alert("请输入标签");
				}
				// 把中文标点转为英文
				tagValues=tagValue.replace(/，/g, ",");
				//获取到的标签
				var stillTag=tagValues.split(",");
				$.each(stillTag,function(index,val){
					console.log(val);
					$("#boxTag").append("<span class=tagbg"+(index%tagsBg.length)+"><a href='#'>"+val+"</a><d class='close'>&#10006;</span></span>");
				});
                $txtTag.val("");
			});
			// 删除标签
				$(document).on("click","#boxTag .close",function () {
					console.log($(this));
					$(this).parent().remove();
				})
			// 点提交
			$("#getTextarea").on("click",function(){

				// 获取标题
        		console.log($("#postTitle").val());
				var txtArr =UE.getEditor('container').getContent();
				// 获取发帖内容
        		console.log(txtArr);
        		var finalTag=[];
				$("#boxTag>span").each(function () {
					$(this).children(".close").remove();
					finalTag.push($(this).children("a").html());
        		});
        		finalTagTxt=finalTag.join(",");
				// 获取发帖的标签
        		console.log(finalTagTxt);
        		//如果标题，内容和标签任一为空
        		if($("#postTitle").val().trim()=="" || txtArr.trim()=="" || finalTagTxt==""){
        			alert("标题，内容和标签均不能为空！");
        		}

        		// 发送Ajax请求：
	$.ajax({
    url: 'http://localhost:8080/lfcfront/addPost',
    dataType:'json'}).done(function(data, status, xhr){
   	var noformatData=JSON.stringify(data.datas,function(key,value){
    	if(key==="tag"){
    		value=value.split(",");
    		value.pop();
    		return value.unique().join(",");
    	}
    	else{
    		return value;
    	}
    },4);
    var formatData=JSON.parse(noformatData); 
    //获取到的数据
    console.log(formatData);
    //判断发表状态state
    if(formatData.state==0){
    alert("发表成功！");
    }
    else if(formatData.state==1){
    alert("文章包含敏感词汇，不能发表！");
    }
    else{
       alert("系统错误！") 
    }
});

});

		});

// 使用 Mock
Mock.mock('http://localhost:8080/lfcfront/addPost',
	{'datas|1': [{
    'title':"@title",
    'tag|1-5':"@constellation",
    "content":"@paragraph",
    "state|-1-1":0
}]});
//定义随机
var Random = Mock.Random;
Random.extend({
    constellation: function(date) {
        var constellations = ['angular', 'js', 'less', 'html5', 'css3', 'gulp', 'git', 'canvas', 'handlebars','react','微信小程序'];
        var newConstellations=[];
        $.each(constellations,function(index,value){
        	value=value+",";
        	newConstellations.push(value);
        })
        return this.pick(newConstellations);
    }
});

//数组去重
Array.prototype.unique = function(){
 var res = [this[0]];
 for(var i = 1; i < this.length; i++){
  var repeat = false;
  for(var j = 0; j < res.length; j++){
   if(this[i] == res[j]){
    repeat = true;
    break;
   }
  }
  if(!repeat){
   res.push(this[i]);
  }
 }
 return res;
}