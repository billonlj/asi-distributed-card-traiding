import HTMLView from "../components/abstract/HTMLView.js";

export default class HomeView extends HTMLView {
    constructor() {
        super();
    }

    async render() {
        this.innerHTML = `
            <header-component></header-component>
            <div class="ui three column grid" style="padding: 1em;" id="home-buttons">
                <div class="column">
                <div class="ui segment fluid">
                    <a class="massive fluid ui button" onclick="${this.bind(() => this.router.redirect("buy"), "redirectBuy")}">
                        Buy
                        <i class="shopping cart icon home-buttons-icon"></i>
                    </a>
                </div>
                </div>
                <div class="column">
                <div class="ui segment fluid">
                    <a class="massive fluid ui button" onclick="${this.bind(() => this.router.redirect("sell"), "redirectSell")}">
                        Sell
                        <i class="money bill alternate outline icon home-buttons-icon"></i>
                    </a>
                </div>
                </div>
                <div class="column">
                <div class="ui segment fluid">
                    <button class="massive fluid ui button" onclick="${this.bind(() => this.router.redirect("rooms"), "redirectRooms")}">
                        Play
                        <i class="game pad icon home-buttons-icon"></i>
                    </button>
                </div>
                </div>
            </div>
        `;
    }
}

customElements.define('home-view', HomeView);