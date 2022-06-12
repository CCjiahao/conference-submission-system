import instance from "./request"

// 登录接口
export const LoginApi = (username: string, password: string, remember: boolean) => instance.post('/api/login', { username: username, password: password, remember: remember })
// 获取当前登录用户的信息
export const GetUserByTokenApi = (token: string) => instance.post('/api/getUserByToken', { token: token })
// 获取邮箱验证码
export const GetVerificationCodeApi = (email: string) => instance.get('/api/getVerificationCode?email=' + email)
// 注册用户
export const RegisterApi = (username: string, name: string, school: string, country: string, expertise: string, password: string, email: string, code: string) => instance.post('/api/register', { username: username, name: name, school: school, country: country, expertise: expertise, password: password, email: email, code: code })
// 根据邮箱获取用户信息
export const GetUserByEmailApi = (email: string) => instance.get('/api/getUserByEmail?email=' + email)
// 提交论文
export const SubmitPaperApi = (token: string, title: string, abstracts: string, expertise: string, collaborators: string, paper: string) => instance.post('/api/submitPaper', { token: token, title: title, abstracts: abstracts, expertise: expertise, collaborators: collaborators, paper: paper })
// 获取全部论文
export const GetPapersApi = () => instance.get('http://localhost:8081/api/getPapers')
// 根据ID获取论文
export const GetPapersByIdApi = (id: string) => instance.get('http://localhost:8081/api/getPaperById?id=' + id)
// 根据作者来获取论文列表
export const GetPapersByAuthorApi = (author: string) => instance.get('http://localhost:8081/api/getPaperByAuthor?author=' + author)
// 根据token和id来删除论文（只能删除待审核和未选中论文）
export const DeletePaperByIdApi = (token: string, id: number) => instance.get('http://localhost:8081/api/deletePaperById?token=' + token + '&id=' + id)