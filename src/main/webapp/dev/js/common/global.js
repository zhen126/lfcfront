$(document).ready(function() {
	$(".nav,.nav_submenu,.move-bg").movebg({
		width: 120 /*滑块的大小*/ ,
		extra: 20 /*额外反弹的距离*/ ,
		speed: 300 /*滑块移动的速度*/ ,
		rebound_speed: 400 /*滑块反弹的速度*/
	});
	// 导航点击切换
	$(".nav_list li").click(function() {
		$(this).addClass("cur").siblings().removeClass("cur");
	});
	// 鼠标经过下拉
	$(".nav li").mouseover(function() {
		$(".nav li").children(".nav_submenu").hide();
		$(this).children(".nav_submenu").show();
	});
	$(".nav_submenu").mouseout(function() {
		$(this).hide();
	});
	$(".header,.content").hover(function() {
		$(".nav_submenu").hide();
	})
});