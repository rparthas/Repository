const fonts = ['Normal','Small','Big']
const colors = ['Red','Green','Blue'];
const sizes = ['4rem','2rem','6rem'];
const fontCalculator =(font) => {

    const index = fonts.findIndex(element => element === font);
    if(index){
        return sizes[index];
    }
    return sizes[0];
}

export {
    fonts,
    colors,
    fontCalculator
}

