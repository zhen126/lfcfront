package com.lfcfront.knowledge.dao.post;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


public interface PostMapper {
/**
 * 查询帖子列表
 * @param pageMap
 * @return
 */
	List<Map<String,Object>> queryPostList(Map<String,Object> pageMap);
	/**
	 * 根据帖子ID查询tag
	 * @param pageMap
	 * @return
	 */
	List<Map<String,Object>> queryPostTag(String id);
	/**
	 * 根据帖子ID查询帖子的评论
	 * @param pageMap
	 * @return
	 */
	List<Map<String,Object>> queryPostComment(String id);
	/**
	 * 根据帖子ID查询帖子的基本信息
	 * @param pageMap
	 * @return
	 */
	Map<String,Object> queryPostInfo(String id);
	/**
	 * 添加帖子
	 * @param title
	 * @param tag
	 * @param content
	 */
	int addPost(Map<String, Object> postMap);
	/**
	 * 添加tag与帖子对应关系
	 * @param tagid
	 * @param postId
	 * @return
	 */
	int addPost_t_r(Map<String, Object> post_t_r);
	/**
	 * 添加Tag表信息
	 * @param tagCan
	 * @return
	 */
	int addTag(Map<String, Object> tagCan);
	/**
	 * 删除帖子的基本信息
	 * @param id
	 * @return
	 */
	int deletePost(String id);
	/**
	 * 删除帖子的tag
	 * @param id
	 * @return
	 */
	int deletePostTag(String id);
	/**
	 * 删除帖子的评论
	 * @param id
	 * @return
	 */
	int deletePostComment(String id);
	/**
	 * 查询个人的帖子
	 * @param pageMap
	 * @return
	 */
	List<Map<String,Object>> queryPostListByAuthor(Map<String,Object> pageMap);
	/**
	 * 查询taglist
	 * @param pageMap
	 * @return
	 */
	List<Map<String,Object>> queryTagList(Map<String,Object> tagMap);
	String queryTagByName(String tag);
	/**
	 * 查询Tag表最大ID
	 * @return
	 */
	String queryMaxTagID();
	/**
	 * 查询Post表最大ID
	 * @return
	 */
	String queryMaxPostID();
	/**
	 * 更新post数据
	 * @param editPost
	 */
	int updatePostInfo(Map<String, Object> editPost);
	/**
	 * 查询帖子的总数
	 * @param pageMap
	 * @return
	 */
	int queryPostCount(Map<String,Object> pageMap);
	/**
	 * 查询我的帖子总数
	 * @param pageMap
	 * @return
	 */
	int queryPostByAuthorCount(Map<String,Object> pageMap);
	/**
	 * 查询tag总数
	 * @param pageMap
	 * @return
	 */
	int queryTagCount(Map<String,Object> pageMap);
}
