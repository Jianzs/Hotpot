# Hotpot API

> 临时域名： http://hotpot.zhengsj.top   
已部署  
发送与接受都是JSON  
以示例表示，不懂的联系我。。。

* [用户](#用户)
	* [登录](#登录)
	* [更新信息](#更新信息)
	* [获取信息](#获取信息)
	* [获取积分](#获取积分)
	* [获取排行](#获取排行)
	* [获取积分历史记录](#获取积分历史记录)
* [任务](#任务)
	* [获取当前任务](#获取当前任务)
	* [获取历史任务](#获取历史任务)
	* [新建任务组](#新建任务组)
	* [完成一个任务](#完成一个任务)
	* [加入任务组](#加入任务组)
	* [打分](#打分)
	* [查看任务详情](#查看任务详情)
	* [查看未开始任务](#查看未开始任务)
	* [访客查看任务详情](#访客查看任务详情)
	* **[获取任务村列表](#获取任务村列表)**

    
## 用户
### 登录
* URL: /user/login
* METHOD: POST
* REQUEST:
    ```JSON
    {
    	"code": "123"
    }
    ```
* RESPONSE:
    * isNew: 是否是第一次使用，如果是，需要提示完善个人信息
    ```JSON
    {
        "status": 0,
        "message": "SUCCESS",
        "data": {
            "isNew": true,
            "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2xlIjowLCJleHAiOjE1Mjk0ODYzMTEsInVzZXJJZCI6Mn0.3Zv0FlLopdZVYWHB8l3Uw3Fz90LyhUBvNLxvn4TRXAU"
        }
    }
    ```

### 更新信息
* URL： /user/info
* METHOD: PUT
* REQUEST:
    * gender 性别
        * 0： 女
        * 1： 男
    ```JSON
    {
    	"username": "jianzs",
    	"avatar": "/image/url",
    	"birthday": "1527638400000",
    	"gender": "1",  
    	"grade": "大一",
    	"collage": "西安电子科技大学",
    	"location": "陕西西安"
    }
    ```
* RESPONSE:
    ```JSON
    {
        "status": 0,
        "message": "SUCCESS",
        "data": {}
    }
    ```

### 获取信息
* URL： /user/info
* METHOD: GET
* REQUEST: Null
* RESPONSE:
    ```json
    {
        "status": 0,
        "message": "SUCCESS",
        "data": {
            "info": {
                "avatar": "/image/url",
                "birthday": "2018-05-30T00:00:00.000+0000",
                "collage": "西安电子科技大学",
                "gender": 1,
                "grade": "大一",
                "username": "jianzs",
                "location": "陕西西安"
            }
        }
    }
    ```

### 获取积分
* URL： /user/score
* METHOD: GET
* REQUEST: Null
* RESPONSE:
    ```json
    {
        "status": 0,
        "message": "SUCCESS",
        "data": {
            "score": {
                "peopleScore": 0,  多人积分
                "personScore": 0   个人积分
            }
        }
    }
    ```

### 获取排行
* URL: /user/rank/{pageNum} 
    * *pageNum 从1开始，且必须有*
* METHOD: GET
* REQUEST: Null
* RESPONSE:
    ```json
    {
        "status": 0,
        "message": "SUCCESS",
        "data": {
            "pageSum": 1,
            "rank": [
                {
                    "peopleScore": 100,
                    "username": "lalalal"
                },
                {
                    "peopleScore": 0,
                    "username": "janzs"
                }
            ]
        }
    }
    ```

### 获取积分历史记录
* URL： /user/score/history/{pageNum}
    * *pageNum 从1开始，且必须有*
* METHOD: GET
* REQUEST: Null
* RESPONSE:
    ```json
    {
        "status": 0,
        "message": "SUCCESS",
        "data": {
            "scores": [
                {
                    "score": 30,
                    "title": "title",
                    "currentDay": "2018-05-11T04:00:00.000+0000"
                }
            ],
            "pageSum": 1
        }
    }
    ```
    
## 任务
### 获取当前任务
* URL： /task/pending
* METHOD: GET
* REQUEST: Null
* RESPONSE:
    * type
        * 0: 多人
        * 1: 个人
    ```json
    {
        "status": 0,
        "message": "SUCCESS",
        "data": {
            "groups": [
                {
                    "totalTask": 3,
                    "title": "title",
                    "finishedTask": 0,
                    "finishedPeople": 0,
                    "groupId": 2,
                    "endTime": "2018-05-30T00:00:00.000+0000",
                    "type": 1
                },
                {
                    "totalTask": 3,
                    "title": "title",
                    "finishedTask": 0,
                    "finishedPeople": 0,
                    "groupId": 1,
                    "endTime": "2018-06-05T00:00:00.000+0000",
                    "type": 0
                }
            ]
        }
    }
    ```

### 获取历史任务
* URL: /task/history/{pageNum} 
    * *pageNum 从1开始，且必须有*
* METHOD: GET
* REQUEST: Null
* RESPONSE:
    * *unfinishedDay 如果大于0，即为未完成*
    * type
        * 0: 多人
        * 1: 个人
    ```json
    {
        "status": 0,
        "message": "SUCCESS",
        "data": {
            "groups": [
                {
                    "title": "title",
                    "finishedPeople": 0,
                    "unfinishedDay": 11,
                    "groupId": 1,
                    "endTime": "2018-05-11T00:00:00.000+0000",
                    "type": 1
                },
                {
                    "title": "title",
                    "finishedPeople": 0,
                    "unfinishedDay": 9,
                    "groupId": 2,
                    "endTime": "2018-04-10T00:00:00.000+0000",
                    "type": 0
                }
            ],
            "pageSum": 1
        }
    }
    ```
    

### 新建任务组
* URL： /task/group
* METHOD: POST
* REQUEST:
    ```json
     {
    	"title": "title", 
    	"startTime": "1526876554508",
    	"endTime": "1527206400000",
    	"type": 1,
    	"isPublic": true,
    	"maxPeople": 10,
    	"items": [ 
    		{
    			"content": "item1"
    		}, 
    		{
    			"content": "item2"
    		},
    		{
    			"content": "item3"
    		}
    	]
    }
    ```
* RESPONSE
    ```json
    {
        "status": 0,
        "message": "SUCCESS",
        "data": {
            "groupId": 1
        }
    }
    ```

### 完成一个任务
 * URL： /task/item
 * METHOD: POST
 * REQUEST:
    ```json
    {
    	"itemId": 1,
    	"groupId": 1
    }
    ```
 * RESPONSE:
    * 成功
        ```json
        {
            "status": 0,
            "message": "SUCCESS",
            "data": {}
        }
        ```
    * 失败
        * code
            * 1 已经完成
        ```json
        {
            "status": 1,
            "message": "Task Already is Finished.",
            "data": {
                "code": 1
            }
        }
        ```
        code | 原因
         -- | --
         1 | 所选任务已标记完成
         2 | 任务组已结束
         3 | 所选任务项不存在
         4 | 没有用户今天的记录信息
         5 | 已过每日截止时间

### 加入任务组
* URL： /task/group/join
* METHOD: POST
* REQUEST:
    ```json
    {
        "groupId": 1
    }
    ```
* RESPONSE
    * 成功
        ```json
        {
            "status": 0,
            "message": "SUCCESS",
            "data": {}
        }
        ```
    * 失败
        * code
            * 1 人数已满
            * 2 任务组已经结束
            * 3 已经在组中
        ```json
        {
            "status": 1,
            "message": "Already in Group.",
            "data": {
                "code": 3
            }
        }
        ```
        
### 打分
* URL: /task/score
* METHOD: POST
* REQUEST:
    ```json
    {
        "groupId": 4,
        "toUserId": 2,
        "score": 5
    }
    ```
* RESPONSE
    ```json
    {
        "status": 0,
        "message": "SUCCESS",
        "data": {}
    }
    ```
    
### 查看任务详情
* URL: /task/group/{groupId}/{userId}
* METHOD: GET
* REQUEST: Null
    * userId
        * **为空时，查看当前用户该任务详情**
        * **非空时，查看指定用户该任务详情**
* RESPONSE:
    * type:
        * 0: 多人任务
        * 1: 个人任务
    * 当`type == 0` 即为多人任务时，才有`members`字段
    ```json
    {
        "status": 0,
        "message": "SUCCESS",
        "data": {
            "summary": {
                "title": "title",
                "endTime": "2018-06-03T00:00:00.000+0000",
                "type": 0
            },
            "members": [
                {
                    "avatar": "url...",
                    "userId": 3,
                    "username": "lalalla"
                }
            ],
            "unfinished": [
                {
                    "itemId": "16",
                    "content": "item1"
                },
                {
                    "itemId": "17",
                    "content": "item2"
                },
                {
                    "itemId": "18",
                    "content": "item3"
                }
            ],
            "finished": []
        }
    }
    ```
    
### 查看未开始任务
* URL： /task/not-start
* METHORD: GET
* REQUEST: Null
* RESPONSE
    ```json
    {
        "status": 0,
        "message": "SUCCESS",
        "data": {
            "tasks": [
                {
                    "title": "title",
                    "groupId": 7,
                    "startTime": "2018-06-05T00:00:00.000+0000", 开始时间
                    "type": 0
                }
            ]
        }
    }
    ```
    
### 访客查看任务详情
* URL： /task/detail/simple/{groupId}
* METHOD: GET
* REQUEST: Null
* RESPONSE
    ```JSON
    {
        "status": 0,
        "message": "SUCCESS",
        "data": {
            "summary": {
                "title": "巴巴爸爸",
                "endTime": "2018-06-03T22:14:00.000+0000",
                "type": 1
            },
            "members": [
                {
                    "avatar": "url...",
                    "userId": 3,
                    "username": "郑守建"
                }
            ],
            "items": [
                {
                    "itemId": "112",
                    "content": "还好好"
                }
            ]
        }
    }
    ```
    
### 获取任务村列表
* URL: /task/village
* ~~URL： /task/village/{page} 弃用~~
    * ~~page 从1开始~~
* METHOD: GET
* REQUEST: Null
* RESPONSE:
    * **没有count了**
    ```json
    {
        "status": 0,
        "message": "SUCCESS",
        "data": {
            "count": 2,  总页数——已删除
            "items": [
                {
                    "groupId": 5,
                    "title": "测试",
                    "startTime": "2018-06-03T22:59:00.000+0000",
                    "endTime": "2018-06-03T22:59:00.000+0000",
                    "sponsorName": "Stranger",
                    "sponsorAvatar": "url...",
                    "sponsorCollage": "XDU",
                    "members": [
                        {
                            "username": "Stranger",
                            "avatar": "url...",
                            "userId": 9
                        }
                    ]
                },
                {
                    "groupId": 6,
                    "title": "测试",
                    "startTime": "2018-06-03T22:59:00.000+0000",
                    "endTime": "2018-06-03T22:59:00.000+0000",
                    "sponsorName": "Stranger",
                    "sponsorAvatar": "url...",
                    "sponsorCollage": "XDU",
                    "members": [
                        {
                            "username": "Stranger",
                            "avatar": "url...",
                            "userId": 9
                        }
                    ]
                },
                {
                    "groupId": 7,
                    "title": "测试",
                    "startTime": "2018-06-03T22:59:00.000+0000",
                    "endTime": "2018-06-03T22:59:00.000+0000",
                    "sponsorName": "Stranger",
                    "sponsorAvatar": "url...",
                    "sponsorCollage": "XDU",
                    "members": [
                        {
                            "username": "Stranger",
                            "avatar": "url...",
                            "userId": 9
                        }
                    ]
                },
                {
                    "groupId": 8,
                    "title": "测试",
                    "startTime": "2018-06-03T22:59:00.000+0000",
                    "endTime": "2018-06-03T22:59:00.000+0000",
                    "sponsorName": "Stranger",
                    "sponsorAvatar": "url...",
                    "sponsorCollage": "XDU",
                    "members": [
                        {
                            "username": "Stranger",
                            "avatar": "url...",
                            "userId": 9
                        }
                    ]
                }
            ]
        }
    }
    ```

