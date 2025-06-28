import React from 'react';
import { Link } from 'react-router-dom';
import { ArrowRight } from 'lucide-react';
import ProductCard from './ProductCard';
import { products } from '../data/products';

export default function FeaturedProducts() {
  const topSellingProducts = products.filter(product => product.isTopSelling);
  const newArrivals = products.filter(product => product.isNew);

  return (
    <div className="py-16">
      <div className="container mx-auto px-4">
        {/* Top Selling Products */}
        <div className="mb-16">
          <div className="flex items-center justify-between mb-8">
            <div>
              <h2 className="text-3xl md:text-4xl font-bold text-gray-800 mb-2">
                Top Handpicked Sarees
              </h2>
              <p className="text-gray-600">Customer favorites and bestselling sarees</p>
            </div>
            <Link
              to="/collections/all"
              className="flex items-center space-x-2 text-amber-600 hover:text-amber-700 font-medium"
            >
              <span>View All</span>
              <ArrowRight className="w-4 h-4" />
            </Link>
          </div>
          
          <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6">
            {topSellingProducts.map((product) => (
              <ProductCard key={product.id} product={product} />
            ))}
          </div>
        </div>

        {/* New Arrivals */}
        <div>
          <div className="flex items-center justify-between mb-8">
            <div>
              <h2 className="text-3xl md:text-4xl font-bold text-gray-800 mb-2">
                New Arrival Sarees
              </h2>
              <p className="text-gray-600">Fresh additions to our collection</p>
            </div>
            <Link
              to="/collections/new"
              className="flex items-center space-x-2 text-amber-600 hover:text-amber-700 font-medium"
            >
              <span>View All</span>
              <ArrowRight className="w-4 h-4" />
            </Link>
          </div>
          
          <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6">
            {newArrivals.map((product) => (
              <ProductCard key={product.id} product={product} />
            ))}
          </div>
        </div>
      </div>
    </div>
  );
}