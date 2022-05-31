import Store from "../utils/store.js";
const routerKey = "router"

export class Router {
    constructor(routes) {
        this.routes = routes;

        this.app = document.querySelector("#app");
        this.loadedRoutes = {}

        const previousRoute = Store.getItem(routerKey);
        if(previousRoute) {
            this.redirect(previousRoute);
        } else {
            this.setRoute(this.routes.find(route => route.root === true));
        }

    }

    async setRoute(route) {
        Store.setItem(routerKey, route.name);
        this._route = route;
        
        let Script;
        if(this._route in this.loadedRoutes) {
            Script = this.loadedRoutes[this._route];
        } else {
            Script = await import(this._route.getScript());
        }
        const element = new Script.default();

        this.app.innerHTML = "";
        this.app.appendChild(element);
    }

    async redirect(routePath) {
        const route = this.routes.find(route => route.name === routePath);
        if(!route) return false; 

        this.setRoute(route);
    }

    getRoute() {
        return this._route;
    }
}

export class Route {
    constructor(name, path, root = false) {
        this.name = name;
        this._path = path ? path !== undefined : `${this.name}.js`;
        this.root = root;
    }

    getScript() {
        return `../views/${this._path}`;
    }
}