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

    connectedCallback() {
        this.innerHTML = `
            <input type="${this.type}" name="${this.name}" placeholder="${this.placeholder}">
      `;
    }
}

customElements.define('input-custom', InputCustom);

export default InputCustom;
