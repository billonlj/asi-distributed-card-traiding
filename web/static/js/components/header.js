import userService from "../services/userService.js";
import HTMLBindableElement from "./abstract/HTMLBindableElement.js";

import { getGlobalComponent } from "../modules/app.js";

const router = {
    'sell': {'header': 'SELL', 'span': 'Sell your card to get money'},
    'buy': {'header': 'BUY', 'span': 'Buy cards'}
}

class Header extends HTMLBindableElement {
    constructor() {
        super();
        this.router = getGlobalComponent("router");

        if (userService.isUserLoggedIn()) {
            this.location = {'header': 'HOME', 'span': ''};
            for (const [key, value] of Object.entries(router)) {
                if(this.router.getRoute() === key) {
                    this.location = value;
                    break;
                }
            }
        } else {
            this.router.redirect('login');
        }

        window.ReloadCustomComponents = {
            "header": this
        }
    }

    logout() {
        userService.logout();
        this.router.redirect("home")
    }

    async render() {
        await userService.refreshUserProfile(false);
        const user = userService.get_user();

        this.innerHTML = `
            <div class="ui clearing segment">
                <h3 class="ui left floated header" onclick="${this.bind(() => this.router.redirect("home"),"redirectHome")}">
                    <i class="money icon"></i>
                    <div class="content">
                        ${this.location?.header}
                        <div class="sub header">${this.location?.span}</div>
                    </div>
                </h3>
                <h3 class="ui right floated header">
                    <button class="ui red button" onclick="${this.bind(this.logout,"logout")}">
                        Logout
                    </button>
                </h3>
                <h3 class="ui right floated header">
                    <i class="user circle outline icon"></i>
                    <div class="content">
                        <span id="userNameId">${user ? user.name + ' ' + user.surname : ''}</span>
                        <div class="sub header"><span>${user ? user.money : 0}</span>$</div>
                    </div>
                </h3>
            </div>
      `;
    }
}

customElements.define('header-component', Header);