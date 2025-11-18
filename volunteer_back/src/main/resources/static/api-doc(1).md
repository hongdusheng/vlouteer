# 高考志愿填报辅助系统 API 接口文档

本文档列出了高考志愿填报辅助系统的所有API接口，包括请求方法、请求路径、参数和返回值等信息。

## 基础URL

所有API的基础URL为: `http://localhost:8080`

## 用户相关接口

### 1. 用户登录

- **URL**: `/admin/login`
- **方法**: POST
- **描述**: 用户登录接口，验证用户名和密码
- **请求参数**:
  ```json
  {
    "username": "用户名",
    "password": "密码"
  }
  ```
  以上参数通过请求体传递
- **返回示例**:
  ```json
  {
    "code": 200,
    "message": "登录成功",
    "data": null
  }
  ```

### 2. 用户登出

- **URL**: `/admin/logout`
- **方法**: POST
- **描述**: 用户登出接口，清除服务端session信息
- **请求参数**: 无
- **返回示例**:
  ```json
  {
    "code": 200,
    "message": "登出成功",
    "data": null
  }
  ```

### 3. 获取用户信息

- **URL**: `/admin/info`
- **方法**: GET
- **描述**: 获取当前登录用户的详细信息，包括权限等
- **请求参数**: 无
- **返回示例**:
  ```json
  {
    "code": 200,
    "message": "操作成功",
    "data": {
      "userId": 1,
      "username": "admin",
      "role": "admin",
      "studentId": null,
      "createTime": "2023-04-12 10:30:45",
      "status": 1
    }
  }
  ```

### 4. 获取用户列表

- **URL**: `/user/userList`
- **方法**: GET
- **描述**: 获取所有用户列表，不分页，主要用于下拉选择等场景
- **请求参数**: 无
- **返回示例**:
  ```json
  {
    "code": 200,
    "message": "操作成功",
    "data": [
      {
        "userId": 1,
        "username": "admin",
        "role": "admin",
        "studentId": null,
        "createTime": "2023-04-12 10:30:45",
        "status": 1
      },
      {
        "userId": 2,
        "username": "student1",
        "role": "student",
        "studentId": "202301001",
        "createTime": "2023-04-12 11:45:22",
        "status": 1
      }
    ]
  }
  ```

### 5. 获取用户分页列表

- **URL**: `/user/getUserPage`
- **方法**: GET
- **描述**: 分页获取用户列表，支持按用户名、角色筛选
- **请求参数**:
  - `pageNum`: 当前页码，从1开始，通过请求路径参数传递
  - `pageSize`: 每页条数，通过请求路径参数传递
  - `username`: 用户名（可选，模糊查询），通过请求路径参数传递
  - `role`: 用户角色（可选，精确匹配），通过请求路径参数传递
- **返回示例**:
  ```json
  {
    "code": 200,
    "message": "操作成功",
    "data": {
      "records": [
        {
          "userId": 1,
          "username": "admin",
          "role": "admin",
          "studentId": null,
          "createTime": "2023-04-12 10:30:45"
        }
      ],
      "total": 3
    }
  }
  ```

### 6. 获取单个用户信息

- **URL**: `/user/getUser`
- **方法**: GET
- **描述**: 获取指定ID的用户详细信息
- **请求参数**:
  - `id`: 用户ID（必填），通过请求路径参数传递
- **返回示例**:
  ```json
  {
    "code": 200,
    "message": "操作成功",
    "data": {
      "userId": 1,
      "username": "admin",
      "role": "admin",
      "studentId": null,
      "createTime": "2023-04-12 10:30:45"
    }
  }
  ```

### 7. 更新用户信息

- **URL**: `/user/updateUser`
- **方法**: POST
- **描述**: 新增或更新用户信息，有id时为更新，无id时为新增
- **请求参数**:
  ```json
  {
    "userId": 1,           // 用户ID，新增时不传
    "username": "用户名",
    "password": "密码",     // 新增时必传，修改时可不传
    "role": "角色",         // admin-管理员，student-学生用户
    "studentId": "学生ID"    // 仅当角色为student时需要
  }
  ```
  以上参数通过请求体传递
- **返回示例**:
  ```json
  {
    "code": 200,
    "message": "操作成功",
    "data": null
  }
  ```

### 8. 删除用户

- **URL**: `/user/deleteUser/{ids}`
- **方法**: DELETE
- **描述**: 删除指定ID的用户，支持批量删除，多个ID用逗号分隔
- **请求参数**:
  - `ids`: 用户ID，路径参数，多个ID用逗号分隔，如`/user/deleteUser/1,2,3`
- **返回示例**:
  ```json
  {
    "code": 200,
    "message": "删除成功",
    "data": null
  }
  ```

### 9. 更新用户状态

- **URL**: `/user/updateStatus`
- **方法**: POST
- **描述**: 更新用户状态，启用或禁用
- **请求参数**:
  ```json
  {
    "userId": 1,     // 用户ID
    "status": 1      // 状态：1-启用，0-禁用
  }
  ```
  以上参数通过请求体传递
- **返回示例**:
  ```json
  {
    "code": 200,
    "message": "操作成功",
    "data": null
  }
  ```

## 学校相关接口

### 1. 获取院校分页列表

- **URL**: `/school/list`
- **方法**: GET
- **描述**: 分页获取院校列表，支持按照多种条件筛选
- **请求参数**:
  - `pageNum`: 当前页码，从1开始，通过请求路径参数传递
  - `pageSize`: 每页条数，通过请求路径参数传递
  - `schoolCode`: 院校代码（可选，精确查询），通过请求路径参数传递
  - `schoolName`: 院校名称（可选，模糊查询），通过请求路径参数传递
  - `level`: 院校层次（可选，精确查询），通过请求路径参数传递
  - `typeId`: 院校类型ID（可选，精确查询），通过请求路径参数传递
  - `province`: 所在省份（可选，模糊查询），通过请求路径参数传递
- **返回示例**:
  ```json
  {
    "code": 200,
    "message": "操作成功",
    "data": {
      "records": [
        {
          "universityId": "C001",
          "universityName": "清华大学",
          "provinceId": 1,
          "province": "北京市",
          "typeId": 1,
          "level": "985",
          "ranking": 1,
          "studentCount": 3000,
          "createTime": "2023-04-12 10:30:45"
        }
      ],
      "total": 15
    }
  }
  ```

### 2. 获取单个院校信息

- **URL**: `/school/detail`
- **方法**: GET
- **描述**: 获取指定ID的院校详细信息
- **请求参数**:
  - `id`: 院校ID（必填）
- **返回示例**:
  ```json
  {
    "code": 200,
    "message": "操作成功",
    "data": {
      "universityId": "C001",
      "universityName": "清华大学",
      "provinceId": 1,
      "province": "北京市",
      "typeId": 1,
      "level": "985",
      "ranking": 1,
      "createTime": "2023-04-12 10:30:45"
    }
  }
  ```

### 3. 新增或更新院校信息

- **URL**: `/school/save`
- **方法**: POST
- **描述**: 新增或更新院校信息，有id时为更新，无id时为新增
- **请求参数**:
  ```json
  {
    "universityId": "C001",
    "universityName": "清华大学",
    "provinceId": 1,
    "typeId": 1,
    "level": "985",
    "ranking": 1
  }
  ```
- **返回示例**:
  ```json
  {
    "code": 200,
    "message": "操作成功",
    "data": null
  }
  ```

### 4. 删除院校

- **URL**: `/school/delete/{ids}`
- **方法**: DELETE
- **描述**: 删除指定ID的院校，支持批量删除，多个ID用逗号分隔
- **请求参数**:
  - `ids`: 院校ID，路径参数，多个ID用逗号分隔，如`/school/delete/C001,C002`
- **返回示例**:
  ```json
  {
    "code": 200,
    "message": "删除成功",
    "data": null
  }
  ```

### 5. 获取院校类型列表

- **URL**: `/school/types`
- **方法**: GET
- **描述**: 获取所有院校类型列表，用于下拉选择
- **请求参数**: 无
- **返回示例**:
  ```json
  {
    "code": 200,
    "message": "操作成功",
    "data": [
      {
        "typeId": 1,
        "typeName": "综合类",
        "description": "综合性大学"
      },
      {
        "typeId": 2,
        "typeName": "理工类",
        "description": "理工类大学"
      }
    ]
  }
  ```

### 6. 通过院校代码查询院校信息

- **URL**: `/school/getByCode`
- **方法**: GET
- **描述**: 根据院校代码查询院校信息
- **请求参数**:
  - `code`: 院校代码（必填）
- **返回示例**:
  ```json
  {
    "code": 200,
    "message": "操作成功",
    "data": {
      "universityId": "C001",
      "universityName": "清华大学",
      "provinceId": 1,
      "province": "北京市",
      "typeId": 1,
      "level": "985",
      "ranking": 1,
      "createTime": "2023-04-12 10:30:45"
    }
  }
  ```

## 专业相关接口

### 1. 获取专业分页列表

- **URL**: `/major/list`
- **方法**: GET
- **描述**: 分页获取专业列表，支持按照多种条件筛选
- **请求参数**:
  - `pageNum`: 当前页码，从1开始，通过请求路径参数传递
  - `pageSize`: 每页条数，通过请求路径参数传递
  - `majorCode`: 专业代码（可选，精确查询），通过请求路径参数传递
  - `majorName`: 专业名称（可选，模糊查询），通过请求路径参数传递
  - `schoolCode`: 院校代码（可选，精确查询），通过请求路径参数传递
  - `schoolName`: 院校名称（可选，模糊查询），通过请求路径参数传递
- **返回示例**:
  ```json
  {
    "code": 200,
    "message": "操作成功",
    "data": {
      "records": [
        {
          "majorId": "M001",
          "majorName": "计算机科学与技术",
          "universityId": "10001",
          "universityName": "北方综合大学",
          "schoolingLength": 4,
          "createTime": "2023-04-12 10:30:45",
          "lineScore": 650
        }
      ],
      "total": 25
    }
  }
  ```

### 2. 获取单个专业信息

- **URL**: `/major/detail`
- **方法**: GET
- **描述**: 获取指定ID的专业详细信息
- **请求参数**:
  - `id`: 专业ID（必填）
- **返回示例**:
  ```json
  {
    "code": 200,
    "message": "操作成功",
    "data": {
      "majorId": "M001",
      "majorName": "计算机科学与技术",
      "disciplineCategoryId": 1,
      "schoolingLength": 4,
      "degreeType": "工学学士",
      "createTime": "2023-04-12 10:30:45",
      "lineScore": 650
    }
  }
  ```

### 3. 新增或更新专业信息

- **URL**: `/major/save`
- **方法**: POST
- **描述**: 新增或更新专业信息，有id时为更新，无id时为新增
- **请求参数**:
  ```json
  {
    "majorId": "M001",
    "majorName": "计算机科学与技术",
    "disciplineCategoryId": 1,
    "schoolingLength": 4,
    "degreeType": "工学学士",
    "lineScore": 650
  }
  ```
- **返回示例**:
  ```json
  {
    "code": 200,
    "message": "操作成功",
    "data": null
  }
  ```

### 4. 删除专业

- **URL**: `/major/delete/{ids}`
- **方法**: DELETE
- **描述**: 删除指定ID的专业，支持批量删除，多个ID用逗号分隔
- **请求参数**:
  - `ids`: 专业ID，路径参数，多个ID用逗号分隔，如`/major/delete/M001,M002`
- **返回示例**:
  ```json
  {
    "code": 200,
    "message": "删除成功",
    "data": null
  }
  ```

### 5. 获取专业分类列表

- **URL**: `/major/categories`
- **方法**: GET
- **描述**: 获取所有专业分类列表，用于下拉选择
- **请求参数**: 无
- **返回示例**:
  ```json
  {
    "code": 200,
    "message": "操作成功",
    "data": [
      {
        "categoryId": 1,
        "categoryName": "工学",
        "code": "08"
      },
      {
        "categoryId": 2,
        "categoryName": "理学",
        "code": "07"
      }
    ]
  }
  ```

### 6. 通过专业代码查询专业信息

- **URL**: `/major/getByCode`
- **方法**: GET
- **描述**: 根据专业代码查询专业信息
- **请求参数**:
  - `code`: 专业代码（必填）
- **返回示例**:
  ```json
  {
    "code": 200,
    "message": "操作成功",
    "data": {
      "majorId": "M001",
      "majorName": "计算机科学与技术",
      "disciplineCategoryId": 1,
      "schoolingLength": 4,
      "degreeType": "工学学士",
      "createTime": "2023-04-12 10:30:45",
      "lineScore": 650
    }
  }
  ```

## 学生相关接口

### 1. 获取学生分页列表

- **URL**: `/student/list`
- **方法**: GET
- **描述**: 分页获取学生列表，支持按照多种条件筛选
- **请求参数**:
  - `pageNum`: 当前页码，从1开始
  - `pageSize`: 每页条数
  - `studentId`: 学生编号（可选，精确查询）
  - `studentName`: 学生姓名（可选，模糊查询）
  - `gender`: 性别（可选，精确查询）
  - `provinceId`: 省份ID（可选，精确查询）
- **返回示例**:
  ```json
  {
    "code": 200,
    "message": "操作成功",
    "data": {
      "records": [
        {
          "studentId": "202301001",
          "studentName": "张三",
          "idCard": "110101200101010101",
          "gender": "男",
          "collegeEntranceExamScore": 680.5,
          "provinceId": 1,
          "provinceName": "北京市",
          "ranking": 100,
          "createTime": "2023-04-12 10:30:45",
          "enrollYear": 2023,
          "graduateYear": 2027
        }
      ],
      "total": 18,
      "size": 10,
      "current": 1
    }
  }
  ```

### 2. 获取单个学生信息

- **URL**: `/student/detail`
- **方法**: GET
- **描述**: 获取指定ID的学生详细信息
- **请求参数**:
  - `id`: 学生ID（必填）
- **返回示例**:
  ```json
  {
    "code": 200,
    "message": "操作成功",
    "data": {
      "studentId": "202301001",
      "studentName": "张三",
      "idCard": "110101200101010101",
      "gender": "男",
      "collegeEntranceExamScore": 680.5,
      "provinceId": 1,
      "provinceName": "北京市",
      "ranking": 100,
      "createTime": "2023-04-12 10:30:45"
    }
  }
  ```

### 3. 新增或更新学生信息

- **URL**: `/student/save`
- **方法**: POST
- **描述**: 新增或更新学生信息，有id时为更新，无id时为新增
- **请求参数**:
  ```json
  {
    "studentId": "202301001",
    "studentName": "张三",
    "idCard": "110101200101010101",
    "gender": "男",
    "collegeEntranceExamScore": 680.5,
    "provinceId": 1,
    "ranking": 100
  }
  ```
  以上参数通过请求体传递
- **返回示例**:
  ```json
  {
    "code": 200,
    "message": "操作成功",
    "data": null
  }
  ```

### 4. 删除学生

- **URL**: `/student/delete/{ids}`
- **方法**: DELETE
- **描述**: 删除指定ID的学生，支持批量删除，多个ID用逗号分隔
- **请求参数**:
  - `ids`: 学生ID，路径参数，多个ID用逗号分隔，如`/student/delete/202301001,202301002`
- **返回示例**:
  ```json
  {
    "code": 200,
    "message": "删除成功",
    "data": null
  }
  ```

## 通用接口

### 1. 获取省份列表

- **URL**: `/common/provinces`
- **方法**: GET
- **描述**: 获取所有省份列表，用于下拉选择
- **请求参数**: 无
- **返回示例**:
  ```json
  {
    "code": 200,
    "message": "操作成功",
    "data": [
      {
        "provinceId": 1,
        "provinceName": "北京市",
        "region": "华北"
      },
      {
        "provinceId": 2,
        "provinceName": "上海市",
        "region": "华东"
      }
    ]
  }
  ```

## 志愿填报相关接口

### 1. 保存志愿信息

- **URL**: `/application/save`
- **方法**: POST
- **描述**: 保存学生填报的志愿信息
- **请求参数**:
  ```json
  {
    "studentId": "202301001",
    "universityId": "C001",
    "majorId": "M001",
    "volunteerOrder": 1
  }
  ```
  以上参数通过请求体传递
- **返回示例**:
  ```json
  {
    "code": 200,
    "message": "保存成功",
    "data": null
  }
  ```

### 2. 获取志愿信息列表

- **URL**: `/application/list`
- **方法**: GET
- **描述**: 获取学生填报的志愿信息列表
- **请求参数**:
  - `studentId`: 学生ID（可选，精确查询），通过请求路径参数传递
  - `pageNum`: 当前页码，从1开始，通过请求路径参数传递
  - `pageSize`: 每页条数，通过请求路径参数传递
- **返回示例**:
  ```json
  {
    "code": 200, 
    "message": "操作成功",
    "data": {
      "records": [
        {
          "relationId": 1,
          "studentId": "202301001",
          "studentName": "张三",
          "universityId": "C001",
          "universityName": "清华大学",
          "majorId": "M001",
          "majorName": "计算机科学与技术",
          "volunteerOrder": 1,
          "province": "北京市",
          "createTime": "2023-04-12 10:30:45",
          "firstVolunteer": {
            "universityId": "C001",
            "universityName": "清华大学",
            "majorId": "M001",
            "majorName": "计算机科学与技术"
          },
          "secondVolunteer": {
            "universityId": "C002",
            "universityName": "北京大学",
            "majorId": "M002",
            "majorName": "软件工程"
          },
          "thirdVolunteer": {
            "universityId": "C003",
            "universityName": "浙江大学",
            "majorId": "M003",
            "majorName": "人工智能"
          }
        }
      ],
      "total": 3
    }
  }
  ```

### 3. 获取单个志愿信息

- **URL**: `/application/detail`
- **方法**: GET
- **描述**: 获取单个志愿信息详情
- **请求参数**:
  - `id`: 志愿ID（必填），通过请求路径参数传递
- **返回示例**:
  ```json
  {
    "code": 200,
    "message": "操作成功",
    "data": {
      "relationId": 1,
      "studentId": "202301001",
      "studentName": "张三",
      "universityId": "C001",
      "universityName": "清华大学",
      "majorId": "M001",
      "majorName": "计算机科学与技术",
      "volunteerOrder": 1,
      "province": "北京市",
      "createTime": "2023-04-12 10:30:45",
      "firstVolunteer": {
        "universityId": "C001",
        "universityName": "清华大学",
        "majorId": "M001",
        "majorName": "计算机科学与技术"
      },
      "secondVolunteer": {
        "universityId": "C002",
        "universityName": "北京大学",
        "majorId": "M002",
        "majorName": "软件工程"
      },
      "thirdVolunteer": {
        "universityId": "C003",
        "universityName": "浙江大学",
        "majorId": "M003",
        "majorName": "人工智能"
      }
    }
  }
  ```

### 4. 删除志愿信息

- **URL**: `/application/delete/{id}`
- **方法**: DELETE
- **描述**: 删除指定ID的志愿信息
- **请求参数**:
  - `id`: 志愿ID，路径参数
- **返回示例**:
  ```json
  {
    "code": 200,
    "message": "删除成功",
    "data": null
  }
  ```

### 5. 提交志愿表

- **URL**: `/application/submit/{id}`
- **方法**: PUT
- **描述**: 提交志愿表
- **请求参数**:
  - `id`: 志愿ID，路径参数
- **返回示例**:
  ```json
  {
    "code": 200,
    "message": "提交成功",
    "data": null
  }
  ```


## 智能推荐相关接口

### 1. 获取志愿推荐

- **URL**: `/recommendation/volunteer`
- **方法**: GET
- **描述**: 根据学生高考分数和历年录取数据，推荐适合的院校和专业组合
- **请求参数**:
  - `studentId`: 学生ID（必填），通过请求路径参数传递
- **返回示例**:
  ```json
  {
    "code": 200,
    "message": "操作成功",
    "data": {
      "student_score": 650,
      "recommendations": [
        {
          "university_name": "清华大学",
          "university_id": "C001",
          "major_name": "计算机科学与技术",
          "major_id": "M001",
          "last_year_admission": 680,
          "score_difference": -30
        },
        {
          "university_name": "北京大学",
          "university_id": "C002",
          "major_name": "软件工程",
          "major_id": "M002",
          "last_year_admission": 675,
          "score_difference": -25
        }
      ]
    }
  }
  ```

### 2. 检测志愿冲突

- **URL**: `/recommendation/conflict`
- **方法**: GET
- **描述**: 检测填报志愿是否存在梯度不合理、志愿倒挂等问题
- **请求参数**:
  - `studentId`: 学生ID（必填），通过请求路径参数传递
- **返回示例**:
  ```json
  {
    "code": 200,
    "message": "操作成功",
    "data": {
      "student_exists": 1,
      "volunteer_count": 3,
      "admission_count": 15,
      "volunteers": [
        {
          "volunteer_order": 1,
          "university_name": "清华大学",
          "major_name": "计算机科学与技术",
          "year": 2022,
          "admission_number": 680
        },
        {
          "volunteer_order": 2,
          "university_name": "北京大学",
          "major_name": "软件工程",
          "year": 2022,
          "admission_number": 675
        }
      ],
      "conflicts": [
        {
          "order1": 1,
          "uni1": "清华大学",
          "major1": "计算机科学与技术",
          "score1": 680,
          "order2": 2,
          "uni2": "北京大学",
          "major2": "软件工程",
          "score2": 675,
          "advice": "梯度不足提示"
        }
      ]
    }
  }
  ```

### 3. 预测录取概率

- **URL**: `/recommendation/probability`
- **方法**: GET
- **描述**: 基于历年录取数据和学生高考成绩，预测被特定院校和专业录取的概率
- **请求参数**:
  - `studentId`: 学生ID（必填），通过请求路径参数传递
  - `universityId`: 院校ID（必填），通过请求路径参数传递
  - `majorId`: 专业ID（必填），通过请求路径参数传递
- **返回示例**:
  ```json
  {
    "code": 200,
    "message": "操作成功",
    "data": {
      "university_name": "清华大学",
      "major_name": "计算机科学与技术",
      "your_score": 650,
      "avg_admission_score": 680,
      "admission_probability": 75,
      "probability_level": "较高",
      "score_std_dev": 10.5,
      "historical_data_count": 50
    }
  }
  ```

### 4. 分析专业就业前景

- **URL**: `/recommendation/employment`
- **方法**: GET
- **描述**: 分析专业的就业前景、薪资水平和行业需求等信息
- **请求参数**:
  - `majorId`: 专业ID（必填），通过请求路径参数传递
- **返回示例**:
  ```json
  {
    "code": 200,
    "message": "操作成功",
    "data": {
      "major_id": "M001",
      "major_name": "计算机科学与技术",
      "discipline_category": "工学",
      "degree_type": "工学学士",
      "schooling_length": 4,
      "employment_status": "就业形势良好",
      "average_employment_rate": 95.8,
      "average_salary": 12000,
      "demand_level": "需求高",
      "industry_outlook": "随着人工智能、大数据等技术的发展，计算机相关专业的就业前景持续看好，未来5-10年内将保持较高需求。",
      "career_advice": "建议在校期间多参与实际项目开发，积累项目经验，同时关注前沿技术发展，提升核心竞争力。",
      "related_majors": "软件工程、人工智能、数据科学与大数据技术"
    }
  }
  ```

### 5. 分析省份分数线

- **URL**: `/recommendation/province`
- **方法**: GET
- **描述**: 分析特定省份和学科门类的分数线趋势
- **请求参数**:
  - `provinceId`: 省份ID（必填），通过请求路径参数传递
  - `disciplineCategoryId`: 学科门类ID（必填），通过请求路径参数传递
- **返回示例**:
  ```json
  {
    "code": 200,
    "message": "操作成功",
    "data": {
      "province_name": "北京市",
      "region": "华北",
      "category_name": "工学",
      "admission_difficulty": "高",
      "min_score_two_years_ago": 605,
      "min_score_last_year": 615,
      "min_score_current_year": 625,
      "avg_score_two_years_ago": 635,
      "avg_score_last_year": 645,
      "avg_score_current_year": 655,
      "max_score_two_years_ago": 665,
      "max_score_last_year": 675,
      "max_score_current_year": 685,
      "score_trend_analysis": "近三年北京市工学类专业分数线呈稳步上升趋势，平均每年上涨10分左右，竞争压力逐年增大。",
      "popular_universities": "清华大学、北京大学、北京航空航天大学、北京理工大学",
      "recommendation": "建议高考分数在655分以上的考生可以考虑报考北京地区的工学类专业，650分以下的考生可考虑其他省份或其他学科门类。"
    }
  }
  ```

### 6. 志愿综合分析

- **URL**: `/recommendation/comprehensive`
- **方法**: GET
- **描述**: 对志愿填报进行全方位分析，评估志愿组合的合理性、风险分布和录取可能性
- **请求参数**:
  - `studentId`: 学生ID（必填），通过请求路径参数传递
- **返回示例**:
  ```json
  {
    "code": 200,
    "message": "操作成功",
    "data": {
      "student_name": "张三",
      "score": 650,
      "rank": 5000,
      "volunteer_count": 3,
      "overall_rating": "良好",
      "rationality_score": 4,
      "safety_score": 3.5,
      "gradient_score": 4.5,
      "major_match_score": 4,
      "volunteers": [
        {
          "order": 1,
          "university_name": "清华大学",
          "major_name": "计算机科学与技术",
          "last_year_score": 680,
          "score_difference": -30,
          "admission_probability": 75,
          "risk_level": "中风险"
        },
        {
          "order": 2,
          "university_name": "北京大学",
          "major_name": "软件工程",
          "last_year_score": 675,
          "score_difference": -25,
          "admission_probability": 80,
          "risk_level": "中风险"
        }
      ],
      "risk_distribution": {
        "high": 33,
        "medium": 33,
        "low": 34
      },
      "risk_analysis": "您的志愿填报风险分布较为均衡，冲刺、稳妥和保底志愿比例适当，总体风险可控。",
      "gradient_analysis": "志愿之间的分数梯度合理，相邻志愿之间分数差异适中，有利于提高录取成功率。",
      "major_match_analysis": "所选专业与您的兴趣和能力匹配度较高，有利于未来的学习和发展。",
      "overall_suggestion": "建议保持当前的志愿填报策略，可以适当考虑增加1-2个保底志愿，进一步降低风险。"
    }
  }
  ```

### 7. 院校专业匹配

- **URL**: `/recommendation/match`
- **方法**: GET
- **描述**: 根据学生高考成绩和专业偏好，匹配最适合的院校和专业组合
- **请求参数**:
  - `studentId`: 学生ID（必填），通过请求路径参数传递
  - `majorPreference`: 专业偏好，多个值用逗号分隔（可选），通过请求路径参数传递
  - `locationPreference`: 地区偏好，多个值用逗号分隔（可选），通过请求路径参数传递
  - `matchCount`: 匹配数量（可选，默认为10），通过请求路径参数传递
  - `matchType`: 匹配类型（可选，可选值：balanced-均衡匹配，university-院校优先，major-专业优先，默认为balanced），通过请求路径参数传递
- **返回示例**:
  ```json
  {
    "code": 200,
    "message": "操作成功",
    "data": [
      {
        "university_name": "清华大学",
        "university_id": "C001",
        "university_level": "985",
        "major_name": "计算机科学与技术",
        "major_id": "M001",
        "major_category": "工学",
        "location": "北京",
        "admission_score": 680,
        "score_difference": -30,
        "match_rate": 92
      },
      {
        "university_name": "北京大学",
        "university_id": "C002",
        "university_level": "985",
        "major_name": "软件工程",
        "major_id": "M002",
        "major_category": "工学",
        "location": "北京",
        "admission_score": 675,
        "score_difference": -25,
        "match_rate": 90
      }
    ]
  }
  ```

### 8. 获取学科门类

- **URL**: `/recommendation/categories`
- **方法**: GET
- **描述**: 获取所有学科门类列表，用于下拉选择
- **请求参数**: 无
- **返回示例**:
  ```json
  {
    "code": 200,
    "message": "操作成功",
    "data": [
      {
        "categoryId": 1,
        "categoryName": "哲学",
        "code": "01"
      },
      {
        "categoryId": 2,
        "categoryName": "经济学",
        "code": "02"
      },
      {
        "categoryId": 3,
        "categoryName": "法学",
        "code": "03"
      },
      {
        "categoryId": 4,
        "categoryName": "教育学",
        "code": "04"
      },
      {
        "categoryId": 5,
        "categoryName": "文学",
        "code": "05"
      },
      {
        "categoryId": 6,
        "categoryName": "历史学",
        "code": "06"
      },
      {
        "categoryId": 7,
        "categoryName": "理学",
        "code": "07"
      },
      {
        "categoryId": 8,
        "categoryName": "工学",
        "code": "08"
      },
      {
        "categoryId": 9,
        "categoryName": "农学",
        "code": "09"
      },
      {
        "categoryId": 10,
        "categoryName": "医学",
        "code": "10"
      },
      {
        "categoryId": 11,
        "categoryName": "管理学",
        "code": "12"
      },
      {
        "categoryId": 12,
        "categoryName": "艺术学",
        "code": "13"
      }
    ]
  }
  ```