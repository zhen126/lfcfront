dev文件夹说明：

css  
	reset.css：公共页面重置
	style.css  页面样式
data
	存放json
font 
	存放字体:iconfont,etc...
img
	存放图片
include
	存放meta:包含title标签,reset.css,style.css,jquery(3.1版本)
	存放公共头部：header.html
	存放公共尾部：footer.html

js
	common/ 存放我们自己用的或者写的js,
			注意：
			1.如果页面用的单独的js尽量和对应html页面的文件名保持同名；
			2.如果插件库依赖jquery,在插件名字前面加上jquery，类似:jquery-库名.js
		global.js：大多页面公共的js
		jquery-backtop.js 依赖于jquery,导航随滚动固定顶部
		jquery.movebg.js  依赖于jquery,导航跟随鼠标特效
	lib/ 常用库
		ueditor 编辑器文件夹
		
		handlebars（版本：4.0.5） 模板语言
		jquery（版本：3.1.1） js公共库
		mock.js:假数据
