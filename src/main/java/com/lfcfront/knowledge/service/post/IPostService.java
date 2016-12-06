package com.lfcfront.knowledge.service.post;

import java.util.Map;

public interface IPostService {
	/**
	 * 分页查询
	 * @param pageIndex
	 * @param pageCapacity
	 * @param user
	 * @param roleId
	 * @return
	 */
	Map<String,Object> queryPostList(int page_start, int page_capacity);
	/**
	 * 查询帖子的详情
	 * @param id
	 * @return
	 */
	Map<String,Object> queryPostInfo(String id);
	/**
	 * 添加帖子
	 * @param title
	 * @param content
	 * @param userid
	 * @param time
	 */
	void addPost(String title, String content, String tag, String userid);
	/**
	 * 删除帖子的信息
	 * @param id
	 * @return
	 */
	int deletePost(String id);
	/**
	 * 查询个人的帖子
	 * @param pageMap
	 * @return
	 */
	Map<String,Object> queryPostListByAuthor(int page_start, int page_capacity,String user_id);
	/**
	 * 查询tagLIst
	 * @param pageMap
	 * @return
	 */
	Map<String,Object> queryTagList();
	/**
	 * 编辑帖子
	 * @param title
	 * @param content
	 * @param tag
	 * @param userid
	 */
	void editPost(String title, String content, String tag, String userid);
}
