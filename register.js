$(document).ready(function(){
	Mock.mock("http://localhost:8080/lfcfront/checkUser",{
	    'state' : 1
	});
	Mock.mock("http://localhost:8080/lfcfront/checkUser",{
	    'state' : 0
	});
	$("#username").blur(function(){
		//验证是否存在；
		var userName = $("#username").val();
		if(!userName){
			alert("用户名不能为空！");
		}else{
			$.ajax({
			    url: 'http://localhost:8080/lfcfront/regist',
			    dataType:'json'
			    }).done(function(data, status, xhr){
			    //console.log(data.state);
			    if(data.state === 0){
			    	alert("已存在");
			    }else if(data.state === 1){
			    	alert("可以注册");
			    }else if(data.state === -1){
			    	alert("系统错误");
			    }
			});
		}
	});
	//注册;
	$("#submitBtn").click(function(){
		var unName = $("#username").val();
		var unPassword = $("#password").val();
		var unCode = $("#code").val();
		if(!unName) {
			alert("用户名不能为空！");
		}else if (!unPassword) {
			alert("密码不能为空");
		}else if (!unCode) {
			alert("验证码不能为空");
		}else{
			$.ajax({
				type: "post",
				data: {
					username : unName,
					paddword : unPassword,
					code : unCode
				},
				url: 'http://localhost:8080/lfcfront/regist',
				dataType : "json"
			}).done(function(data, status, xhr){
			    console.log(data.state);
			    if(data.state === 0){
			    	alert("注册成功");
			    }else if(data.state === 1){
			    	alert("用户名已存在");
			    }else if(data.state === -1){
			    	alert("系统错误");
			    }else if(data.state === 2){
			    	alert("验证码错误");
			    }
			});
		}
	});
});