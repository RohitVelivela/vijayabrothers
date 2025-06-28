import { Product } from '../types';

export const products: Product[] = [
  {
    id: '1',
    name: 'Elegant Banarasi Silk Saree',
    price: 4999,
    originalPrice: 6999,
    image: 'https://images.pexels.com/photos/8839999/pexels-photo-8839999.jpeg',
    images: [
      'https://images.pexels.com/photos/8839999/pexels-photo-8839999.jpeg',
      'https://images.pexels.com/photos/8963463/pexels-photo-8963463.jpeg',
      'https://images.pexels.com/photos/7945653/pexels-photo-7945653.jpeg'
    ],
    category: 'Pattu',
    collection: 'bridal',
    fabric: 'Banarasi Silk',
    description: 'Exquisite Banarasi silk saree with intricate golden zari work. Perfect for weddings and special occasions.',
    isNew: false,
    isTopSelling: true,
    isOnSale: true,
    tags: ['Bridal', 'Traditional', 'Festive'],
    availability: 'in-stock'
  },
  {
    id: '2',
    name: 'Pure Cotton Mangalagiri Saree',
    price: 1299,
    image: 'https://images.pexels.com/photos/7945627/pexels-photo-7945627.jpeg',
    images: [
      'https://images.pexels.com/photos/7945627/pexels-photo-7945627.jpeg',
      'https://images.pexels.com/photos/7511669/pexels-photo-7511669.jpeg'
    ],
    category: 'Mangalagiri',
    collection: 'casual',
    fabric: 'Cotton',
    description: 'Comfortable pure cotton Mangalagiri saree with traditional temple border design.',
    isNew: true,
    isTopSelling: false,
    tags: ['Casual', 'Comfortable', 'Traditional'],
    availability: 'in-stock'
  },
  {
    id: '3',
    name: 'Designer Fancy Net Saree',
    price: 2499,
    originalPrice: 3499,
    image: 'https://images.pexels.com/photos/8963485/pexels-photo-8963485.jpeg',
    images: [
      'https://images.pexels.com/photos/8963485/pexels-photo-8963485.jpeg',
      'https://images.pexels.com/photos/8840003/pexels-photo-8840003.jpeg'
    ],
    category: 'Fancy',
    collection: 'party',
    fabric: 'Net',
    description: 'Stunning fancy net saree with embellished work, perfect for parties and celebrations.',
    isNew: false,
    isTopSelling: true,
    isOnSale: true,
    tags: ['Party Wear', 'Designer', 'Embellished'],
    availability: 'limited'
  },
  {
    id: '4',
    name: 'Handwoven Ikkath Silk Saree',
    price: 3299,
    image: 'https://images.pexels.com/photos/7511621/pexels-photo-7511621.jpeg',
    images: [
      'https://images.pexels.com/photos/7511621/pexels-photo-7511621.jpeg',
      'https://images.pexels.com/photos/7511623/pexels-photo-7511623.jpeg'
    ],
    category: 'Ikkath',
    collection: 'traditional',
    fabric: 'Silk',
    description: 'Authentic handwoven Ikkath silk saree showcasing traditional Andhra craftsmanship.',
    isNew: true,
    isTopSelling: false,
    tags: ['Handwoven', 'Traditional', 'Silk'],
    availability: 'in-stock'
  },
  {
    id: '5',
    name: 'Pure Linen Summer Saree',
    price: 1899,
    image: 'https://images.pexels.com/photos/7945632/pexels-photo-7945632.jpeg',
    images: [
      'https://images.pexels.com/photos/7945632/pexels-photo-7945632.jpeg',
      'https://images.pexels.com/photos/7945629/pexels-photo-7945629.jpeg'
    ],
    category: 'Linen',
    collection: 'summer',
    fabric: 'Linen',
    description: 'Breathable pure linen saree ideal for summer occasions with elegant prints.',
    isNew: true,
    isTopSelling: false,
    tags: ['Summer', 'Breathable', 'Printed'],
    availability: 'in-stock'
  },
  {
    id: '6',
    name: 'Royal Bridal Red Saree',
    price: 8999,
    originalPrice: 12999,
    image: 'https://images.pexels.com/photos/8840001/pexels-photo-8840001.jpeg',
    images: [
      'https://images.pexels.com/photos/8840001/pexels-photo-8840001.jpeg',
      'https://images.pexels.com/photos/8963475/pexels-photo-8963475.jpeg'
    ],
    category: 'Pattu',
    collection: 'bridal',
    fabric: 'Silk',
    description: 'Magnificent royal red bridal saree with heavy embroidery and gold work.',
    isNew: false,
    isTopSelling: true,
    isOnSale: true,
    tags: ['Bridal', 'Heavy Work', 'Royal'],
    availability: 'limited'
  }
];

export const categories = [
  { id: 'pattu', name: 'Pattu Sarees', image: 'https://images.pexels.com/photos/8839999/pexels-photo-8839999.jpeg' },
  { id: 'ikkath', name: 'Ikkath Sarees', image: 'https://images.pexels.com/photos/7511621/pexels-photo-7511621.jpeg' },
  { id: 'linen', name: 'Linen Sarees', image: 'https://images.pexels.com/photos/7945632/pexels-photo-7945632.jpeg' },
  { id: 'mangalagiri', name: 'Mangalagiri', image: 'https://images.pexels.com/photos/7945627/pexels-photo-7945627.jpeg' },
  { id: 'cotton', name: 'Cotton Sarees', image: 'https://images.pexels.com/photos/7511669/pexels-photo-7511669.jpeg' },
  { id: 'fancy', name: 'Fancy Sarees', image: 'https://images.pexels.com/photos/8963485/pexels-photo-8963485.jpeg' },
  { id: 'bridal', name: 'Bridal Collection', image: 'https://images.pexels.com/photos/8840001/pexels-photo-8840001.jpeg' }
];