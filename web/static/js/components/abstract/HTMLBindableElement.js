class HTMLBindableElement extends HTMLElement {
  constructor() {
    super();
  }

  bind(callback, name) {
    if (!callback) {
      return '';
    }
    window[name] = callback;
    return `window.${name}();`
  }

  render() {}

  connectedCallback() {
    this.render();
  }
}

export default HTMLBindableElement;