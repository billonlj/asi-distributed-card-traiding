import { Router, Route } from "./router.js";

const app = async () => {
    window.components = {};

    setGlobalComponent('app', this);
    setGlobalComponent('router', new Router([
        new Route("home", undefined, true),
        new Route("buy"),
        new Route("lobby"),
        new Route("login"),
        new Route("register"),
        new Route("rooms"),
        new Route("sell"),
    ]));
};

export function setGlobalComponent(name, component) {
    window.components[name] = component;
}

export function getGlobalComponent(name) {
    return window.components[name];
}

document.addEventListener("DOMContentLoaded", app);