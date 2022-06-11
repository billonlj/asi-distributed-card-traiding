class InputCustom extends HTMLElement {
    constructor(type,name,placeholder) {
        super();
        this.name = name;
        this.type = type;
        this.placeholder = placeholder;
    }

    bind(callback, name) {
        if (!callback) {
            return '';
        }
        window[name]=callback;
        return `window.${name}();`
    }

    getValue() {
        return this.firstElementChild.value
    }

    render() {
        this.innerHTML = `
            <input type="${this.type}" name="${this.name}" placeholder="${this.placeholder}">
        `;
    }

    setText(text) {
        this.placeholder = text;
        this.render();
    }

    connectedCallback() {
        this.render();
    }


}

customElements.define('input-custom', InputCustom);

export default InputCustom;
