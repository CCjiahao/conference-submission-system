import instance from "./request"

// 登录接口
export const LoginApi = (username: string, password: string, remember: boolean) => instance.post('/api/login', { username: username, password: password, remember: remember })
// 获取当前登录用户的信息
export const GetUserByTokenApi = (token: string) => instance.post('/api/getUserByToken', { token: token })
// 获取邮箱验证码
export const GetVerificationCodeApi = (email: string) => instance.get('/api/getVerificationCode?email=' + email)
// 获取邮箱验证码
export const GetVerificationCodeByUsernameAndEmailApi = (username: string, email: string) => instance.get('/api/getVerificationCodeByUsernameAndEmail?username=' + username + '&email=' + email)
// 注册用户
export const RegisterApi = (username: string, name: string, school: string, country: string, expertise: string, password: string, email: string, code: string) => instance.post('/api/register', { username: username, name: name, school: school, country: country, expertise: expertise, password: password, email: email, code: code })
// 修改用户接口
export const UpdateUserApi = (username: string, name: string, school: string, country: string, expertise: string, email: string, role: string) => instance.post('/api/updateUser', { username: username, name: name, school: school, country: country, expertise: expertise, email: email, role: role})
// 忘记密码接口
export const ForgetApi = (username: string, password: string, email: string, code: string) => instance.post('/api/forget', { username: username, password: password, email: email, code: code })
// 根据邮箱获取用户信息
export const GetUserByEmailApi = (email: string) => instance.get('/api/getUserByEmail?email=' + email)
// 提交论文
export const SubmitPaperApi = (token: string, title: string, abstracts: string, expertise: string, collaborators: string, paper: string) => instance.post('/api/submitPaper', { token: token, title: title, abstracts: abstracts, expertise: expertise, collaborators: collaborators, paper: paper })
// 获取全部论文
export const GetPapersApi = () => instance.get('/api/getPapers')
// 根据ID获取论文
export const GetPapersByIdApi = (id: string) => instance.get('/api/getPaperById?id=' + id)
// 根据作者来获取论文列表
export const GetPapersByAuthorApi = (author: string) => instance.get('/api/getPapersByAuthor?author=' + author)
// 筛选审稿者可以审阅的论文列表
export const GetPapersByReviewerApi = (token: string) => instance.get('/api/getPapersByReviewer?token=' + token)
// 筛选审稿者已经审阅的论文列表
export const GetReviewedPapersByReviewerApi = (token: string) => instance.get('/api/getReviewedPapersByReviewer?token=' + token)
// 根据token和id来删除论文（只能删除待审核和未选中论文）
export const DeletePaperByIdApi = (token: string, id: number) => instance.get('/api/deletePaperById?token=' + token + '&id=' + id)
//  提交评审意见
export const SubmitReviewApi = (token: string, paperId: string, isAssociated: string, logic: number, sci: number, innovation: number, passOrReject: string, suggestion: string) => instance.post('/api/submitReview', { token: token, paperId: paperId, isAssociated: isAssociated, logic: logic, sci: sci, innovation: innovation, passOrReject: passOrReject, suggestion: suggestion })
// 根据paperid获取review
export const GetReviewByPaperIdApi = (paperid: string) => instance.get('/api/getReviewByPaperId?paperid=' + paperid)
// 提交rebuttal
export const SubmitRebuttalApi = (token: string, reviewer: string, paperId: string, content: string) => instance.post('/api/submitRebuttal', { token: token, reviewer: reviewer, paperId: paperId, content: content })
// 根绝paperid获取rebuttal
export const GetRebuttalByPaperIdApi = (paperid: string) => instance.get('/api/getRebuttalByPaperId?paperid=' + paperid)
// 根绝paperid获取文章详情
export const GetPaperDetailByIdApi = (paperid: number) => instance.get('/api/getPaperDetailById?id=' + paperid)
// 获得用户列表
export const GetUsers = () => instance.get('/api/getUsers')
