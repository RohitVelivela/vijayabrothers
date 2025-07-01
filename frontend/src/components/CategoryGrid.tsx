import React from 'react';
import BanarasiCategoryCard from './BanarasiCategoryCard';
import { Category } from '../data/categories';

interface CategoryGridProps {
  categories: Category[];
  title: string;
  showAll?: boolean;
}

const CategoryGrid: React.FC<CategoryGridProps> = ({ categories, title, showAll = false }) => {
  const displayCategories = showAll ? categories : categories.slice(0, 8);

  const ornateCategories = [
    { 
      title: "Banarasi Kora Saree", 
      image: "https://images.pexels.com/photos/8839833/pexels-photo-8839833.jpeg?auto=compress&cs=tinysrgb&w=800",
    },
    { 
      title: "Soft Silk Sarees", 
      image: "https://images.pexels.com/photos/9054599/pexels-photo-9054599.jpeg?auto=compress&cs=tinysrgb&w=800",
    },
    { 
      title: "Fancy Sarees", 
      image: "https://images.pexels.com/photos/8839825/pexels-photo-8839825.jpeg?auto=compress&cs=tinysrgb&w=800",
    },
    { 
      title: "Tissue Silk Sarees", 
      image: "https://images.pexels.com/photos/9054595/pexels-photo-9054595.jpeg?auto=compress&cs=tinysrgb&w=800",
    },
    { 
      title: "Kuppadam Sico Sarees", 
      image: "https://images.pexels.com/photos/8839827/pexels-photo-8839827.jpeg?auto=compress&cs=tinysrgb&w=800",
    },
    { 
      title: "Chiniya Silk Sarees", 
      image: "https://images.pexels.com/photos/8839846/pexels-photo-8839846.jpeg?auto=compress&cs=tinysrgb&w=800",
    },
    { 
      title: "Office Wear Sarees", 
      image: "https://images.pexels.com/photos/9054593/pexels-photo-9054593.jpeg?auto=compress&cs=tinysrgb&w=800",
    },
    { 
      title: "Kalanjali Silk Sarees", 
      image: "https://images.pexels.com/photos/9054597/pexels-photo-9054597.jpeg?auto=compress&cs=tinysrgb&w=800",
    }
  ];

  return (
    <section className="py-20 bg-gradient-to-br from-amber-50 via-orange-50 to-red-50 relative overflow-hidden">
      {/* Background Pattern */}
      <div className="absolute inset-0 opacity-5">
        <div className="absolute inset-0" style={{
          backgroundImage: `url("data:image/svg+xml,%3Csvg width='60' height='60' viewBox='0 0 60 60' xmlns='http://www.w3.org/2000/svg'%3E%3Cg fill='none' fill-rule='evenodd'%3E%3Cg fill='%23d97706' fill-opacity='0.4'%3E%3Cpath d='M30 30c0-11.046-8.954-20-20-20s-20 8.954-20 20 8.954 20 20 20 20-8.954 20-20zm0 0c0 11.046 8.954 20 20 20s20-8.954 20-20-8.954-20-20-20-20 8.954-20 20z'/%3E%3C/g%3E%3C/g%3E%3C/svg%3E")`,
          backgroundSize: '60px 60px'
        }}></div>
      </div>

      <div className="container mx-auto px-4 relative z-10">
        {/* Elegant Section Header */}
        <div className="text-center mb-16">
          <div className="flex items-center justify-center mb-8">
            <div className="flex-1 h-px bg-gradient-to-r from-transparent via-amber-400 to-transparent max-w-32"></div>
            <div className="px-8">
              <h2 className="font-playfair text-4xl md:text-5xl font-bold text-gray-800 mb-4 tracking-wide">
                {title}
              </h2>
              <div className="flex items-center justify-center space-x-2">
                <div className="w-8 h-px bg-amber-400"></div>
                <div className="w-3 h-3 bg-amber-400 rounded-full"></div>
                <div className="w-16 h-1 bg-gradient-to-r from-amber-400 to-red-500 rounded-full"></div>
                <div className="w-3 h-3 bg-red-500 rounded-full"></div>
                <div className="w-8 h-px bg-red-500"></div>
              </div>
            </div>
            <div className="flex-1 h-px bg-gradient-to-l from-transparent via-amber-400 to-transparent max-w-32"></div>
          </div>
          <p className="font-cinzel text-gray-600 text-lg italic tracking-wide">
            Discover our heritage collection of premium handwoven sarees
          </p>
        </div>

        {/* Banarasi Category Cards Grid */}
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-8 lg:gap-10">
          {ornateCategories.map((category, index) => (
            <BanarasiCategoryCard
              key={category.title}
              title={category.title}
              image={category.image}
              className="transform hover:scale-105 transition-all duration-500"
            />
          ))}
        </div>

        {/* View All Button */}
        {!showAll && categories.length > 8 && (
          <div className="text-center mt-16">
            <button className="group relative bg-gradient-to-r from-red-600 to-amber-600 text-white px-12 py-4 rounded-full font-playfair font-bold text-lg hover:from-red-700 hover:to-amber-700 transition-all duration-500 shadow-2xl hover:shadow-3xl transform hover:-translate-y-1 overflow-hidden">
              {/* Button shimmer effect */}
              <div className="absolute inset-0 bg-gradient-to-r from-transparent via-white/20 to-transparent -translate-x-full group-hover:translate-x-full transition-transform duration-1000"></div>
              <span className="relative z-10">View Complete Collection</span>
            </button>
          </div>
        )}
      </div>
    </section>
  );
};

export default CategoryGrid;