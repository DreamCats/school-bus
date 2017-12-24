from PIL import ImageDraw


class Myimage_deal(object):
    def __init__(self,image):
        self.image = image
        # 去噪参数
    def set_denoise_init(self, G, N, Z):
        self.G = G  # Integer 图像二值化阀值
        self.N = N  # Integer 降噪率 0 <N <8
        self.Z = Z  # Integer 降噪次数

    # 灰度处理
    def set_shades(self):
        self.image = self.image.convert('L')

    # 二值判断,如果确认是噪声,用改点的上面一个点的灰度进行替换
    # 该函数也可以改成RGB判断的,具体看需求如何
    def get_Pixel(self,image, x, y, G, N):
        L = self.image.getpixel((x, y))
        if L > G:
            L = True
        else:
            L = False

        nearDots = 0
        if L == (self.image.getpixel((x - 1, y - 1)) > G):
            nearDots += 1
        if L == (self.image.getpixel((x - 1, y)) > G):
            nearDots += 1
        if L == (self.image.getpixel((x - 1, y + 1)) > G):
            nearDots += 1
        if L == (self.image.getpixel((x, y - 1)) > G):
            nearDots += 1
        if L == (self.image.getpixel((x, y + 1)) > G):
            nearDots += 1
        if L == (self.image.getpixel((x + 1, y - 1)) > G):
            nearDots += 1
        if L == (self.image.getpixel((x + 1, y)) > G):
            nearDots += 1
        if L == (self.image.getpixel((x + 1, y + 1)) > G):
            nearDots += 1

        if nearDots < N:
            return self.image.getpixel((x, y - 1))
        else:
            return None

    # 降噪
    # 根据一个点A的RGB值，与周围的8个点的RBG值比较，设定一个值N（0 <N <8），当A的RGB值与周围8个点的RGB相等数小于N时，此点为噪点
    # G: Integer 图像二值化阀值
    # N: Integer 降噪率 0 <N <8
    # Z: Integer 降噪次数
    # 输出
    #  0：降噪成功
    #  1：降噪失败
    def set_denoise(self):
        draw = ImageDraw.Draw(self.image)
        for i in range(0, self.Z):
            for x in range(1, self.image.size[0] - 1):
                for y in range(1, self.image.size[1] - 1):
                    color = self.get_Pixel(self.image, x, y, self.G, self.N)
                    if color != None:
                        draw.point((x, y), color)

    # 保存图片
    def set_save(self,file_str):
        self.image.save(file_str)

