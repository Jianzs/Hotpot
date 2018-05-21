# Hotpot API

> 临时域名： http://hotpot.zhengsj.top   
未部署  
发送与接受都是JSON  
以示例表示，不懂的联系我。。。

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
            "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2xlIjowLCJleHAiOjE1Mjk0ODMyMDAsInVzZXJJZCI6MX0.GfJg0McLSuDLcvSoXpPRgpiqMEwdSsTVes58mtFAEyE"
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

## 任务
### 获取当前任务
* URL： /task/pending
* METHOD: GET
* REQUEST: Null
* RESPONSE:
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
                    "endTime": "2018-05-30T00:00:00.000+0000"
                },
                {
                    "totalTask": 3,
                    "title": "title",
                    "finishedTask": 0,
                    "finishedPeople": 0,
                    "groupId": 1,
                    "endTime": "2018-06-05T00:00:00.000+0000"
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
                    "endTime": "2018-05-11T00:00:00.000+0000"
                },
                {
                    "title": "title",
                    "finishedPeople": 0,
                    "unfinishedDay": 9,
                    "groupId": 2,
                    "endTime": "2018-04-10T00:00:00.000+0000"
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
        "data": {}
    }
    ```

