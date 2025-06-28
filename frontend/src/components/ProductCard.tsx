import React from 'react';
import { Link } from 'react-router-dom';
import { Heart, ShoppingCart, Eye } from 'lucide-react';
import { Product } from '../types';
import { useAppContext } from '../context/AppContext';

interface ProductCardProps {
  product: Product;
  className?: string;
}

export default function ProductCard({ product, className = '' }: ProductCardProps) {
  const { state, dispatch } = useAppContext();
  const isInWishlist = state.wishlist.includes(product.id);
  const isInCart = state.cart.some(item => item.productId === product.id);

  const handleAddToCart = (e: React.MouseEvent) => {
    e.preventDefault();
    e.stopPropagation();
    dispatch({ type: 'ADD_TO_CART', payload: product });
  };

  const handleToggleWishlist = (e: React.MouseEvent) => {
    e.preventDefault();
    e.stopPropagation();
    dispatch({ type: 'TOGGLE_WISHLIST', payload: product.id });
  };

  const discount = product.originalPrice ? 
    Math.round(((product.originalPrice - product.price) / product.originalPrice) * 100) : 0;

  return (
    <div className={`group relative bg-white rounded-lg shadow-md overflow-hidden hover:shadow-xl transition-all duration-300 transform hover:-translate-y-1 ${className}`}>
      <Link to={`/product/${product.id}`}>
        <div className="relative aspect-[3/4] overflow-hidden">
          <img
            src={product.image}
            alt={product.name}
            className="w-full h-full object-cover group-hover:scale-105 transition-transform duration-300"
          />
          
          {/* Badges */}
          <div className="absolute top-2 left-2 flex flex-col gap-1">
            {product.isNew && (
              <span className="bg-green-500 text-white px-2 py-1 text-xs font-medium rounded">
                New
              </span>
            )}
            {product.isTopSelling && (
              <span className="bg-red-500 text-white px-2 py-1 text-xs font-medium rounded">
                Top Selling
              </span>
            )}
            {product.isOnSale && discount > 0 && (
              <span className="bg-amber-500 text-white px-2 py-1 text-xs font-medium rounded">
                {discount}% OFF
              </span>
            )}
            {product.isSoldOut && (
              <span className="bg-gray-500 text-white px-2 py-1 text-xs font-medium rounded">
                Sold Out
              </span>
            )}
          </div>

          {/* Availability indicator */}
          {product.availability === 'limited' && !product.isSoldOut && (
            <div className="absolute top-2 right-2">
              <span className="bg-orange-500 text-white px-2 py-1 text-xs font-medium rounded">
                Limited Stock
              </span>
            </div>
          )}

          {/* Quick Actions */}
          <div className="absolute inset-0 bg-black bg-opacity-0 group-hover:bg-opacity-20 transition-all duration-300 flex items-center justify-center">
            <div className="opacity-0 group-hover:opacity-100 transition-opacity duration-300 flex space-x-2">
              <button className="bg-white p-2 rounded-full shadow-lg hover:bg-gray-100">
                <Eye className="w-5 h-5 text-gray-700" />
              </button>
              <button
                onClick={handleToggleWishlist}
                className={`p-2 rounded-full shadow-lg ${
                  isInWishlist ? 'bg-red-500 text-white' : 'bg-white text-gray-700 hover:bg-gray-100'
                }`}
              >
                <Heart className="w-5 h-5" fill={isInWishlist ? 'currentColor' : 'none'} />
              </button>
              <button
                onClick={handleAddToCart}
                disabled={product.isSoldOut}
                className={`p-2 rounded-full shadow-lg ${
                  product.isSoldOut
                    ? 'bg-gray-300 text-gray-500 cursor-not-allowed'
                    : isInCart
                    ? 'bg-green-500 text-white'
                    : 'bg-white text-gray-700 hover:bg-gray-100'
                }`}
              >
                <ShoppingCart className="w-5 h-5" />
              </button>
            </div>
          </div>
        </div>

        <div className="p-4">
          <h3 className="font-semibold text-gray-800 mb-2 line-clamp-2 group-hover:text-amber-600 transition-colors">
            {product.name}
          </h3>
          
          <div className="flex items-center justify-between mb-2">
            <div className="flex items-center space-x-2">
              <span className="text-lg font-bold text-gray-900">
                ₹{product.price.toLocaleString()}
              </span>
              {product.originalPrice && (
                <span className="text-sm text-gray-500 line-through">
                  ₹{product.originalPrice.toLocaleString()}
                </span>
              )}
            </div>
          </div>

          <div className="flex items-center justify-between text-sm text-gray-600">
            <span className="bg-gray-100 px-2 py-1 rounded">{product.fabric}</span>
            <span className="capitalize">{product.availability.replace('-', ' ')}</span>
          </div>

          {product.tags.length > 0 && (
            <div className="mt-2 flex flex-wrap gap-1">
              {product.tags.slice(0, 2).map((tag) => (
                <span key={tag} className="text-xs bg-amber-100 text-amber-700 px-2 py-1 rounded">
                  {tag}
                </span>
              ))}
            </div>
          )}
        </div>
      </Link>
    </div>
  );
}