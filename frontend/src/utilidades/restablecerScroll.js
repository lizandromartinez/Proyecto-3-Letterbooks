/** Restablece el scroll del documento a la parte superior. */
export function restablecerScroll() {
    window.scrollTo(0, 0);
    document.documentElement.scrollTop = 0;
    document.body.scrollTop = 0;
}

/** Envuelve un onClick para hacer scroll antes de navegar. */
export function scrollAlNavegar(handler) {
    return (event) => {
        restablecerScroll();
        handler?.(event);
    };
}
