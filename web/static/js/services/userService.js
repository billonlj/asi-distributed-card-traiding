import Store from "../utils/store.js"; 
import API from "../utils/axios.js";
import {updateToken} from "../utils/axios.js";


const get_user = () => Store.getItem(user_key);
const get_token = () => get_user()?.token;
const get_user_id = () => parseInt(get_user()?.idUser);
const logout = () => {
    Store.removeItem(user_key);
}

const user_key = 'user';
const root = 'user';


export default {
    async register(data) {
        try {
            if (data.passwordUser === data.passwordConfirm) {
                const reponse = await API.post(`${root}/register`, data)
                return reponse.status == 200
            }
            return false;
        } catch (error) {
            return false
        }
    },
    async login(data) {
        try {
            const response = await API.post(`${root}/login`, data);
            const user = response.data;
            Store.setItem(user_key, user);
            updateToken()

            return true;
        } catch (error) {
            return false;
        }
    },
    async refreshUserProfile(refreshHeader = true) {
        try {
            const response = await API.post(`${root}/profile`);
            const newUserProfile = response.data;
            delete newUserProfile.token;

            Store.setItem(user_key, {
                ...get_user(),
                ...newUserProfile,
            });

            if(refreshHeader) 
                window.ReloadCustomComponents['header'].render();
            
            return true;
        } catch (error) {
            logout();
            return false;
        }
    },
    get_user,
    get_token,
    get_user_id,
    logout,
    isUserLoggedIn() {
        return get_user() != null;
    }
}