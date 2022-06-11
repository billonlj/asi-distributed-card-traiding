export default {
    setItem(key, item) {
        localStorage.setItem(key, JSON.stringify(item));
    },
    getItem(key) {
        return JSON.parse(localStorage.getItem(key));
    },
    removeItem(key) {
        localStorage.removeItem(key);
    }
}