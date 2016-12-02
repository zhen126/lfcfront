
var gulp = require('gulp'),
    //压缩html
    cheerio = require('gulp-cheerio'),
    htmlmin = require('gulp-minify-html'),
    //压缩js
    uglify = require('gulp-uglify'),
    //压缩css
    minifycss = require('gulp-minify-css'),
    //复制文件
    flatten = require('gulp-flatten'),

    //提取公共代码
    fileinclude  = require('gulp-file-include');
//提起公共代码
gulp.task('fileinclude', function() {
    var options = {
        removeComments: true,  //清除HTML注释
        collapseWhitespace: true,  //压缩HTML
        collapseBooleanAttributes: true,  //省略布尔属性的值 <input checked="true"/> ==> <input checked />
        removeEmptyAttributes: true,  //删除所有空格作属性值 <input id="" /> ==> <input />
        removeScriptTypeAttributes: true,  //删除<script>的type="text/javascript"
        removeStyleLinkTypeAttributes: true,  //删除<style>和<link>的type="text/css"
        minifyJS: true,  //压缩页面JS
        minifyCSS: true  //压缩页面CSS
    };
    // 适配static中所有文件夹下的所有html，排除static下的include文件夹中html
    gulp.src(['static/**/*.html','!static/include/**.html'])
        .pipe(fileinclude({
            prefix: '@@',
            basepath: '@file'
        }))
        .pipe(htmlmin(options))
        .pipe(gulp.dest('dist'));
});
//压缩js
gulp.task('scripts',function () {
    gulp.src('./static/js/*.js')
        .pipe(uglify())
        .pipe(gulp.dest('./dist/js'));
});
//压缩css
gulp.task('style',function () {
    gulp.src('./static/css/*.css')
        .pipe(minifycss())
        .pipe(gulp.dest('./dist/css'))
});
//将相关图片复制到dist
gulp.task('copyimages',function () {
    gulp.src('./static/images/*')
        .pipe(flatten())
        .pipe(gulp.dest('./dist/images'))
});
gulp.task('copyiconfont',function () {
    gulp.src('./static/iconfont/*')
        .pipe(flatten())
        .pipe(gulp.dest('./dist/iconfont'))
})
gulp.task('copyueditor',function () {
    return gulp.src('./static/ueditor/**/*')
        .pipe(gulp.dest('./dist/ueditor'))
})
//复制文件
gulp.task('copy',['copyimages','copyiconfont','copyueditor']);
//压缩文件
gulp.task('zip',['style','scripts','fileinclude']);
//默认任务
gulp.task('default',['copy','zip']);


