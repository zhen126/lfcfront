var lr = require('tiny-lr'),
    server = lr(),
    gulp = require('gulp'),
    htmlmin = require('gulp-htmlmin'),
    minifyCss = require('gulp-minify-css'),
    uglify = require('gulp-uglify'),
    webserver  = require('gulp-webserver'),
    zip = require('gulp-zip'),
    copy = require('gulp-copy'),
    clean = require('gulp-clean'),
    cheerio = require('gulp-cheerio'),
    fileinclude  = require('gulp-file-include'),
    config = require('./configs.json');

//压缩javascript 文件，压缩后文件放入dist/js下
gulp.task('minifyjs',function(){
    gulp.src('./dev/js/**/*.js')
        .pipe(uglify())
        .pipe(gulp.dest('./dist/js'))
});
//压缩css
gulp.task('minifycss',function () {
    gulp.src('dev/css/**/*.css')
        .pipe(minifyCss())
        .pipe(gulp.dest('./dist/css'))
});
//压缩html  合并公共代码
gulp.task('htmlmin', function() {
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
    gulp.src(['dev/**/*.html','!dev/include/**.html'])
        .pipe(fileinclude({
            prefix: '@@',
            basepath: '@file'
        }))
        .pipe(htmlmin(options))
        .pipe(gulp.dest('./dist'));
});
//开启本地 Web 服务器功能
gulp.task('webserver', function() {
    gulp.src( './dev/' )
        .pipe(webserver({
            host:             config.localserver.host,
            port:             config.localserver.port,
            livereload:       true,
            directoryListing: false
        }));
});

//通过浏览器打开本地 Web服务器 路径
gulp.task('openbrowser', function() {
    opn( 'http://' + config.localserver.host + ':' + config.localserver.port );
});

//将相关项目文件复制到build 文件夹下
gulp.task('copy', function() {
    //根目录文件
    gulp.src('./dev/images/*')
        .pipe(gulp.dest('./dist/images'));
    gulp.src('./dev/data/**/*')
        .pipe(gulp.dest('./dist/data'));
    gulp.src('./dev/font/**/*')
        .pipe(gulp.dest('./dist/font'))
});

//文件监控
gulp.task('watch', function () {
    server.listen(35729, function (err) {
        if (err){
            return console.log(err);
        }
    });
    gulp.watch(['./dev/*.html','./dev/*.css','./dev/js/*.js'],  function (e) {
        server.changed({
            body: {
                files: [e.path]
            }
        });
    });
});
//默认任务
gulp.task('default', function(){
    console.log('Starting Gulp tasks, enjoy coding!');
    gulp.run('watch');
    gulp.run('webserver');
    // gulp.run('openbrowser');
});
//打包
gulp.task('build',['copy','htmlmin','minifycss','minifyjs']);