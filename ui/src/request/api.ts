import instance from "./request"

// 登录接口
export const LoginApi = (username: string, password: string, remember: boolean) => instance.post('http://localhost:8081/api/login', {username: username, password: password, remember: remember})
// 获取当前登录用户的信息
export const GetUserByTokenApi = (token: string) => instance.post('http://localhost:8081/api/getUserByToken', {token: token})
