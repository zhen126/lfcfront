package com.lfcfront.knowledge.serviceImpl.post;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.lfcfront.knowledge.dao.post.PostMapper;
import com.lfcfront.knowledge.service.post.IPostService;

@Service
public class PostServiceImpl implements IPostService{

	Logger log = Logger.getLogger(PostServiceImpl.class);

	@Autowired
	private PostMapper postMapper;


	@Override
	public Map<String, Object> queryPostList(int page_start,
			int page_capacity) {
		// TODO Auto-generated method stub
		Map<String,Object> pageMap=new HashMap<String, Object>();
		pageMap.put("page_start", page_start);
		pageMap.put("page_capacity", page_capacity);
		List<Map<String, Object>> postList= postMapper.queryPostList(pageMap);
		for(Map<String,Object> postMap:postList){
			List<Map<String, Object>> tagList=postMapper.queryPostTag(postMap.get("id").toString());
			String tagStr="";
			for(Map<String,Object> tagMap:tagList){
				tagStr=tagStr+tagMap.get("tagName")+",";
			}
			if(!StringUtils.isEmpty(tagStr)){
				tagStr=tagStr.substring(0, tagStr.length()-1);
			}
			postMap.put("tag", tagStr);	
		}
		Map<String,Object> postMap=new HashMap<String, Object>();
		int count=postMapper.queryPostCount(pageMap);
		postMap.put("totalCount", count);
		postMap.put("postList", postList);
		return postMap;
	}


	@Override
	public Map<String, Object> queryPostInfo(String id) {
		Map<String, Object> postMap= new HashMap<String, Object>();
		postMap=postMapper.queryPostInfo(id);
		List<Map<String, Object>> tagList=postMapper.queryPostTag(id);
		String tagStr="";
		for(Map<String,Object> tagMap:tagList){
			tagStr=tagStr+tagMap.get("tagName")+",";
		}
		tagStr=tagStr.substring(0, tagStr.length()-1);
		postMap.put("tag", tagStr);	
		List<Map<String, Object>> commentList=postMapper.queryPostComment(id);
		postMap.put("commentList", commentList);	
		return postMap;
	}


	@Override
	public int deletePost(String id) {
		postMapper.deletePostComment(id);
		postMapper.deletePostTag(id);		
		return postMapper.deletePost(id);
	}


	@Override
	public Map<String, Object> queryPostListByAuthor(
			int page_start, int page_capacity,String user_id) {
		Map<String,Object> pageMap=new HashMap<String, Object>();
		pageMap.put("page_start", page_start);
		pageMap.put("page_capacity", page_capacity);
		pageMap.put("id", user_id);
		List<Map<String, Object>> postList= postMapper.queryPostListByAuthor(pageMap);
		for(Map<String,Object> postMap:postList){
			List<Map<String, Object>> tagList=postMapper.queryPostTag(postMap.get("id").toString());
			String tagStr="";
			for(Map<String,Object> tagMap:tagList){
				tagStr=tagStr+tagMap.get("tagName")+",";
			}
			tagStr=tagStr.substring(0, tagStr.length()-1);
			postMap.put("tag", tagStr);	
		}
		Map<String,Object> postMap=new HashMap<String, Object>();
		int count=postMapper.queryPostByAuthorCount(pageMap);
		postMap.put("totalCount", count);
		postMap.put("postList", postList);
		return postMap;
	}


	@Override
	public Map<String, Object> queryTagList() {
		// TODO Auto-generated method stub
		List<Map<String,Object>> tagList= postMapper.queryTagList(new HashMap<String, Object>());
		Map<String,Object> tagMap=new HashMap<String, Object>();
		int count=postMapper.queryTagCount(tagMap);
		tagMap.put("totalCount", count);
		tagMap.put("tagList", tagList);
		return tagMap;
	}
 
	/**
	 * 添加文章，标题对应关系
	 * @param tagids
	 * @param postId
	 */
	public void addPost_rel(ArrayList<Integer> tagids,int postId){
		for(int tagId:tagids){
			Map<String, Object> post_t_r = new HashMap<>();
			post_t_r.put("tagid", tagId);
			post_t_r.put("postid", postId);
			postMapper.addPost_t_r(post_t_r);
		}
	}
	/**
	 * 添加tag信息
	 * @param tags
	 * @param maxTagId
	 * @param tagids
	 */
	 
	public void addTag(String[] tags,int maxTagId,ArrayList<Integer> tagids){
	for (String tag : tags) {
		maxTagId = maxTagId + 1;
		Map<String, Object> tagCan = new HashMap<>();
		String tagExitId = postMapper.queryTagByName(tag);
		int exitTagId=0;
		if (tagExitId != null) {
			exitTagId = Integer.parseInt(tagExitId);
			tagids.add(exitTagId);
		}else{
			tagids.add(maxTagId);
			tagCan.put("id", maxTagId);
			tagCan.put("tag", tag);
			postMapper.addTag(tagCan);
			}
	}
	}
	@Override
	public void addPost(String title, String content, String tagss, String userid) {
		// TODO Auto-generated method stub
		String[] tags=tagss.split(",");
		ArrayList<Integer> tagids=new ArrayList<Integer>();
		int maxTagId=0;
		int maxPostId=0;
		if (postMapper.queryMaxTagID() != null) {
			maxTagId = Integer.parseInt(postMapper.queryMaxTagID());
		}
		if (postMapper.queryMaxPostID() != null) {
			maxPostId = Integer.parseInt(postMapper.queryMaxPostID());
		}
		int postId=maxPostId+1;
		//添加tag表信息
		addTag(tags,maxTagId,tagids);
		//添加post表信息
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Timestamp time=Timestamp.valueOf(sdf.format(date));
		Map<String, Object> postMap = new HashMap<String, Object>();
		postMap.put("id", postId);
		postMap.put("title", title);
		postMap.put("content", content);
		postMap.put("userid", userid);
		postMap.put("time", time);
		postMapper.addPost(postMap);
		//添加tag,文章对应关系
		addPost_rel(tagids,postId);
	}


	@Override
	public void editPost(String postid,String title,String content,String tagss) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Timestamp time=Timestamp.valueOf(sdf.format(new Date()));
		String[] tags=tagss.split(",");
		ArrayList<Integer> tagids=new ArrayList<Integer>();
		int maxTagId=0;
		if (postMapper.queryMaxTagID() != null) {
			maxTagId = Integer.parseInt(postMapper.queryMaxTagID());
		}
		//更新post表
		Map<String, Object> editPost = new HashMap<>();	
		editPost.put("id", Integer.parseInt(postid));
		editPost.put("title", title);
		editPost.put("time", time);
		editPost.put("content", content);
		postMapper.updatePostInfo(editPost);
		//查找postID删除对应关系
		postMapper.deletePostTag(postid);
		//添加tag信息
		addTag(tags,maxTagId,tagids);
		//重新添加对应关系
		addPost_rel(tagids,Integer.parseInt(postid));
	}

}
