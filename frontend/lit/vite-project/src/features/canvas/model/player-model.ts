export class PlayerModel {
  x: number;
  y: number;
  width: number;
  height: number;
  imageString: string;

  constructor(
    x: number,
    y: number,
    width: number,
    height: number,
    imageString: string
  ) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.imageString = imageString;
  }

  getX(): number {
    return this.x;
  }

  getY(): number {
    return this.y;
  }

  getWidth(): number{
    return this.width;
  }

   getHeight(): number{
    return this.height;
  }

   getImage(): HTMLImageElement{
    const image = new Image();
    image.src = this.imageString;
    return image;
  }

  move(direction: string){
    console.log(direction)
  }
}


