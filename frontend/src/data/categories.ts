export interface Category {
  id: number;
  title: string;
  image: string;
  description: string;
  itemCount: number;
}

export const categories: Category[] = [
  {
    id: 1,
    title: "Banarasi Silk Sarees",
    image: "https://images.pexels.com/photos/8839833/pexels-photo-8839833.jpeg?auto=compress&cs=tinysrgb&w=800",
    description: "Luxurious handwoven silk sarees from Varanasi",
    itemCount: 45
  },
  {
    id: 2,
    title: "Kanjivaram Silk",
    image: "https://images.pexels.com/photos/9054599/pexels-photo-9054599.jpeg?auto=compress&cs=tinysrgb&w=800",
    description: "Premium South Indian silk sarees",
    itemCount: 38
  },
  {
    id: 3,
    title: "Cotton Handloom",
    image: "https://images.pexels.com/photos/8839825/pexels-photo-8839825.jpeg?auto=compress&cs=tinysrgb&w=800",
    description: "Comfortable daily wear cotton sarees",
    itemCount: 52
  },
  {
    id: 4,
    title: "Designer Sarees",
    image: "https://images.pexels.com/photos/9054595/pexels-photo-9054595.jpeg?auto=compress&cs=tinysrgb&w=800",
    description: "Contemporary designer collections",
    itemCount: 29
  },
  {
    id: 5,
    title: "Bridal Collection",
    image: "https://images.pexels.com/photos/8839827/pexels-photo-8839827.jpeg?auto=compress&cs=tinysrgb&w=800",
    description: "Exquisite bridal and wedding sarees",
    itemCount: 24
  },
  {
    id: 6,
    title: "Georgette Sarees",
    image: "https://images.pexels.com/photos/9054593/pexels-photo-9054593.jpeg?auto=compress&cs=tinysrgb&w=800",
    description: "Elegant georgette fabric sarees",
    itemCount: 41
  },
  {
    id: 7,
    title: "Chiffon Collection",
    image: "https://images.pexels.com/photos/8839846/pexels-photo-8839846.jpeg?auto=compress&cs=tinysrgb&w=800",
    description: "Light and graceful chiffon sarees",
    itemCount: 33
  },
  {
    id: 8,
    title: "Tissue Silk",
    image: "https://images.pexels.com/photos/9054597/pexels-photo-9054597.jpeg?auto=compress&cs=tinysrgb&w=800",
    description: "Shimmering tissue silk sarees",
    itemCount: 27
  },
  {
    id: 9,
    title: "Mangalagiri Cotton",
    image: "https://images.pexels.com/photos/8839831/pexels-photo-8839831.jpeg?auto=compress&cs=tinysrgb&w=800",
    description: "Traditional Andhra Pradesh cotton sarees",
    itemCount: 35
  },
  {
    id: 10,
    title: "Pochampally Ikat",
    image: "https://images.pexels.com/photos/9054599/pexels-photo-9054599.jpeg?auto=compress&cs=tinysrgb&w=800",
    description: "Geometric patterned Pochampally sarees",
    itemCount: 22
  },
  {
    id: 11,
    title: "Chanderi Silk",
    image: "https://images.pexels.com/photos/8839825/pexels-photo-8839825.jpeg?auto=compress&cs=tinysrgb&w=800",
    description: "Lightweight Madhya Pradesh silk sarees",
    itemCount: 31
  },
  {
    id: 12,
    title: "Tussar Silk",
    image: "https://images.pexels.com/photos/8839833/pexels-photo-8839833.jpeg?auto=compress&cs=tinysrgb&w=800",
    description: "Natural textured tussar silk sarees",
    itemCount: 26
  },
  {
    id: 13,
    title: "Organza Sarees",
    image: "https://images.pexels.com/photos/9054595/pexels-photo-9054595.jpeg?auto=compress&cs=tinysrgb&w=800",
    description: "Crisp and elegant organza sarees",
    itemCount: 19
  },
  {
    id: 14,
    title: "Net Sarees",
    image: "https://images.pexels.com/photos/8839827/pexels-photo-8839827.jpeg?auto=compress&cs=tinysrgb&w=800",
    description: "Modern net fabric sarees",
    itemCount: 23
  },
  {
    id: 15,
    title: "Linen Sarees",
    image: "https://images.pexels.com/photos/9054593/pexels-photo-9054593.jpeg?auto=compress&cs=tinysrgb&w=800",
    description: "Comfortable and breathable linen sarees",
    itemCount: 28
  },
  {
    id: 16,
    title: "Embroidered Sarees",
    image: "https://images.pexels.com/photos/8839846/pexels-photo-8839846.jpeg?auto=compress&cs=tinysrgb&w=800",
    description: "Intricately embroidered designer sarees",
    itemCount: 34
  }
];

export const featuredCategories = categories.slice(0, 8);
export const allCategories = categories;