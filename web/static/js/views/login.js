import HTMLView from "../components/abstract/HTMLView.js";
import InputCustom from "../components/input.js";

import userService from "../services/userService.js";

export default class LoginView extends HTMLView {
    constructor() {
        super();

        this.emailInput = new InputCustom("text", "email", "E-mail address");
        this.pwdInput = new InputCustom("password", "password", "password");
    }

    render() {
        this.innerHTML = `
            <div class="ui middle aligned center aligned grid" style="height: 100%;">
                <div class="column" style="max-width: 450px;">
                    <h2 class="ui teal image header">
                        <div class="content">
                            Log-in to your account
                        </div>
                    </h2>
                    
                    <div class="ui large form">
                        <div class="ui stacked segment">
                            <span id="error" class="error"></span>
                            <div class="field">
                                <div id="emailLoginContainer" class="ui left icon input">
                                    <i class="user icon"></i>
                                </div>
                            </div>
                            <div class="field">
                                <div id="pwdLoginContainer" class="ui left icon input">
                                    <i class="lock icon"></i>
                                </div>
                            </div>
                            <button class="ui fluid large teal button" onclick="${this.bind(() => this.submit(), "login")}">Login</button>
                        </div>

                        <div class="ui error message"></div>
                    </div>


                    <div class="ui message">
                        New to us? <a onclick="${this.bind(() => this.router.redirect("register"), "redirectRegister")}">Sign Up</a>
                    </div>
                </div>
            </div>
      `;

        this.querySelector("#emailLoginContainer").appendChild(this.emailInput);
        this.querySelector("#pwdLoginContainer").appendChild(this.pwdInput);
    }

    setError(errorTxt) {
        const error = this.querySelector("#error");
        error.innerHTML = errorTxt;
    }

    async submit() {
        const email = this.emailInput.getValue()
        const password = this.pwdInput.getValue()
        const isLogged = await userService.login({ email, password })

        if (isLogged) {
            this.router.redirect('home');
        } else {
            this.setError("Une erreur est survenue lors de la connexion")
        }
    }
}

customElements.define('login-view', LoginView);
