export default function authHeader() {
    const currentUser = localStorage.getItem("user");
    let user = null;
    if (currentUser)
        user = JSON.parse(currentUser);

    if (user && user.accessToken) {
        return { Authorization: 'Bearer ' + user.accessToken };
    } else {
        return { Authorization: '' };
    }
}