import React from 'react';
import ProductCard from './ProductCard';
import { Product } from '../data/products';

interface ProductGridProps {
  products: Product[];
  showNavigationArrows?: boolean;
  onProductClick?: (productId: number) => void;
}

const ProductGrid: React.FC<ProductGridProps> = ({ 
  products, 
  showNavigationArrows = false,
  onProductClick 
}) => {
  return (
    <div className="relative">
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">
        {products.map((product) => (
          <ProductCard
            key={product.id}
            id={product.id}
            title={product.title}
            price={product.price}
            originalPrice={product.originalPrice}
            image={product.image}
            badge={product.badge}
            badgeColor={product.badgeColor}
            category={product.category}
            fabric={product.fabric}
            color={product.color}
            rating={product.rating}
            reviews={product.reviews}
            inStock={product.inStock}
            onClick={() => onProductClick?.(product.id)}
          />
        ))}
      </div>
      
      {showNavigationArrows && (
        <>
          <button className="absolute left-0 top-1/2 transform -translate-y-1/2 -translate-x-6 bg-white rounded-full p-3 shadow-lg hover:shadow-xl transition-all duration-300 z-10">
            <svg className="w-6 h-6 text-gray-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M15 19l-7-7 7-7" />
            </svg>
          </button>
          <button className="absolute right-0 top-1/2 transform -translate-y-1/2 translate-x-6 bg-white rounded-full p-3 shadow-lg hover:shadow-xl transition-all duration-300 z-10">
            <svg className="w-6 h-6 text-gray-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 5l7 7-7 7" />
            </svg>
          </button>
        </>
      )}
    </div>
  );
};

export default ProductGrid;