import instance from "./request"

// 登录接口
export const LoginApi = (username: string, password: string, remember: boolean) => instance.post('/api/login', {username: username, password: password, remember: remember})
// 获取当前登录用户的信息
export const GetUserByTokenApi = (token: string) => instance.post('/api/getUserByToken', {token: token})
// 获取邮箱验证码
export const GetVerificationCodeApi = (email: string) => instance.get('/api/getVerificationCode?email=' + email)
// 注册用户
export const RegisterApi = (username: string, name: string, school: string, country: string, expertise: string, password: string, email: string, code: string) => instance.post('/api/register', {username: username, name: name, school: school, country: country, expertise: expertise, password: password, email: email, code: code})
