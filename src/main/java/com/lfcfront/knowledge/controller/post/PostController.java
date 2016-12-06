package com.lfcfront.knowledge.controller.post;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lfcfront.knowledge.model.user.User;
import com.lfcfront.knowledge.service.post.IPostService;
import com.lfcfront.knowledge.service.user.IUserService;

@Controller
@RequestMapping("/")
public class PostController {

	@Autowired
	private IPostService postService;
	Logger log = Logger.getLogger(PostController.class);
/**
 * 查询帖子列表
 * @param request
 * @param page_start
 * @param page_capacity
 * @param session
 * @return
 */
	@ResponseBody
	@RequestMapping(value = "postList")
	public Map<String,Object> postList(HttpServletRequest request,
			int page_start, int page_capacity, HttpSession session) {
		page_start=(page_start-1)*page_capacity;
		return postService.queryPostList(page_start, page_capacity);
	}	
	/**
	 * 查询帖子详情
	 * @param request
	 * @param id
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "postDetail")
	public Map<String,Object> postDetail(HttpServletRequest request,
			String id, HttpSession session) {
        Map<String,Object> postList=postService.queryPostInfo(id);
		return postList;
	}
	/**
	 * 删除一个帖子
	 * 
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("delPost")
	public Map<String, String> delPost(String id) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("state", "0");
		try {
			postService.deletePost(id);
		} catch (Exception e) {
			log.error(e);
			map.put("state", "1");
		}
		return map;
	}
	/**
	 * 我的帖子
	 * @param request
	 * @param page_start
	 * @param page_capacity
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "myPost")
	public Map<String,Object> myPost(HttpServletRequest request,
			int page_start, int page_capacity, HttpSession session) {
		page_start=(page_start-1)*page_capacity;
		User user=(User) session.getAttribute("user");
		String user_id=String.valueOf(user.getId());
		return postService.queryPostListByAuthor(page_start, page_capacity, user_id);
	}	
	/**
	 * 标签列表
	 * @param request
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "tagList")
	public Map<String,Object> tagList(HttpServletRequest request, HttpSession session) {
		return postService.queryTagList();
	}	
	/**
	 * 添加帖子
	 * @param request
	 * @param title
	 * @param tag
	 * @param content
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "addPost")
	public Map<String, Object> addPost(HttpServletRequest request,
			String title, String tag,String content,HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>();
		User user=(User) session.getAttribute("user");
		String userid=String.valueOf(user.getId());
		String msg="";
		int state=9;
		if (title != null && tag != null && content != null) {
			if (title.contains("法轮功") || tag.contains("法轮功") ||content.contains("法轮功")) {
				state = 1;
				msg = "文章包含敏感词汇，不能发表！";
			} else {
				state = 0;
				msg = "添加成功！";
				postService.addPost(title,content,tag,userid);
			}
		} else if (title == null || tag == null || content == null) {
			state = -999;
			msg = "缺少必输项！";
		} else {
			state = -1;
			msg = "系统错误！";
		}
		result.put("state", state);
		result.put("msg", msg);
		return result;
	}	
	/**
	 * 更新帖子
	 * @param request
	 * @param title
	 * @param tag
	 * @param content
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "editPost")
	public Map<String, Object> editPost(HttpServletRequest request,
			String id, String title, String tag,String content,HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>();
		String msg="";
		int state=9;
		if (id!=null && title != null && content != null) {
			if (title.contains("法轮功") || tag.contains("法轮功") || content.contains("法轮功")) {
				state = 1;
				msg = "文章包含敏感词汇，不能发表！";
			} else {
				state = 0;
				msg = "更新成功！";
				postService.editPost(id,title,content,tag);
			}
		} else if (id==null) {
			state = -999;
			msg = "缺少必输项ID！";
		} else {
			state = -1;
			msg = "系统错误！";
		}
		result.put("state", state);
		result.put("msg", msg);
		return result;
	}	
}
