export interface Product {
  id: string;
  name: string;
  price: number;
  originalPrice?: number;
  image: string;
  images: string[];
  category: string;
  collection: string;
  fabric: string;
  description: string;
  isNew?: boolean;
  isTopSelling?: boolean;
  isSoldOut?: boolean;
  isOnSale?: boolean;
  tags: string[];
  videoUrl?: string;
  availability: 'in-stock' | 'limited' | 'out-of-stock';
}

export interface CartItem {
  productId: string;
  quantity: number;
  product: Product;
}

export interface Customer {
  name: string;
  phone: string;
  email: string;
  address: {
    street: string;
    city: string;
    state: string;
    pincode: string;
  };
}

export interface Order {
  id: string;
  customer: Customer;
  items: CartItem[];
  total: number;
  status: 'pending' | 'confirmed' | 'shipped' | 'delivered' | 'cancelled';
  createdAt: string;
  paymentMethod: 'razorpay' | 'cod';
  paymentStatus: 'pending' | 'paid' | 'failed';
}

export interface FilterOptions {
  priceRange: [number, number];
  colors: string[];
  fabrics: string[];
  availability: string[];
}