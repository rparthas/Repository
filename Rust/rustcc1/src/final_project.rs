use image::DynamicImage;
use clap::Arg;
use clap::App;
use std::str::FromStr;

struct CropArgs{
    x:u32,
    y:u32,
    w:u32,
    h:u32,
}


pub fn execute() {
    let option =Arg::with_name("command")
                .short("c")
                .long("command")
                .multiple(true)
                .help("Sets the option to do")
                .index(3)
                .takes_value(true);
    
    let input =Arg::with_name("input")
                .short("i")
                .long("input")
                .value_name("FILE")
                .help("Input file to use")
                .index(1)
                .takes_value(true);
    
    let output =Arg::with_name("output")
                .short("o")
                .long("output")
                .value_name("FILE")
                .help("Output file to use")
                .index(2)
                .takes_value(true);

    let matches = App::new("Image")
        .version("1.0")
        .about("Image Processing")
        .author("Rajagopal")
        .arg(input)
        .arg(output)
        .arg(option)
        .get_matches();

    let mut commands = matches.values_of("command").unwrap().peekable();
    let infile = matches.value_of("input").unwrap().to_string();
    let mut img = image::open(infile).expect("Failed to open INFILE.");
    let outfile = matches.value_of("output").unwrap().to_string();


    loop {
        if commands.peek() == None{
            break;
        }
        match commands.next() {
            Some("blur") => {
                let (value,next) = get_value(commands.peek(), 2.0);
                img = blur(& mut img,  value);
                if next {
                    commands.next();
                }
            },
    
            Some("brighten") => {
                let (value,next) = get_value(commands.peek(), 2);
                img = brighten(& mut img,value);
                if next {
                    commands.next();
                }
            },
    
            Some("crop") => {
                img = crop(& mut img,CropArgs { x: 10, y: 50, w: 100, h: 100 });
            },
            
            Some("rotate") => {
                let (value,next) = get_value(commands.peek(), 90);
                img = rotate(& mut img, value);
                if next {
                    commands.next();
                }
            },
    
            Some("invert") => {
                invert(& mut img);
            },
    
            Some("grayscale") => {
                grayscale(& mut img);
            },
    
            _ => {
                print_usage_and_exit();
            },
        }
    }
    img.save(outfile).expect("Failed writing OUTFILE.");

}

fn print_usage_and_exit() {
    println!("USAGE (when in doubt, use a .png extension on your filenames)");
    println!("blur INFILE OUTFILE");
}

fn blur(img: &mut DynamicImage,blur_value: f32) -> DynamicImage {
    return img.blur(blur_value);
}

fn brighten(img: &mut DynamicImage,value: i32) -> DynamicImage {
    return img.brighten(value);
}

fn crop(img: &mut DynamicImage, crop_args:CropArgs)-> DynamicImage {
    return img.crop(crop_args.x,crop_args.y,crop_args.w,crop_args.h);
}

fn rotate(img: &mut DynamicImage, rotate_value : i32)-> DynamicImage {

    return match rotate_value {
        90 => img.rotate90(),
        180 => img.rotate180(),
        270 => img.rotate270(),
        _ => img.rotate90()
    };
}


fn invert(img: &mut DynamicImage)  {
    img.invert();
}

fn grayscale(img: &mut DynamicImage) {
    img.grayscale();
}

fn get_value<T:FromStr> (arg: Option<&&str>, default_value:T) -> (T,bool){
    return match arg{
        None => (default_value,false),
        Some(y) => match y.parse::<T>(){
            Ok(x) => (x,true),
            _ => (default_value,false)
        }
    };
}

//cargo run --release dyson.png out.png blur 3.0 rotate 180 invert grayscale brighten 50 
