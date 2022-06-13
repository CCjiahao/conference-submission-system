export const checkEmail = (email: string) => {
    var pattern = /^([a-zA-Z0-9]+[_|\_|\.|-]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
    if (pattern.test(email)) {
        return true;
    } else {
        return false;
    }
}