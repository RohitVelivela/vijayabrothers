import React, { useState } from 'react';
import { Search, ShoppingCart, Menu, X } from 'lucide-react';

const Header: React.FC = () => {
  const [isMenuOpen, setIsMenuOpen] = useState(false);

  return (
    <header className="bg-white shadow-lg sticky top-0 z-50">
      <div className="container mx-auto px-6">
        {/* Main Header Row - Premium Layout */}
        <div className="flex items-center justify-between h-20">
          {/* Logo - Optimized for Clarity */}
          <div className="flex items-center flex-shrink-0">
            <img
              src="/VB logo white back.png"
              alt="Vijay Brothers"
              className="h-16 w-auto object-contain filter brightness-110 contrast-110"
              style={{
                imageRendering: 'crisp-edges',
                WebkitImageRendering: 'crisp-edges',
                msImageRendering: 'crisp-edges'
              }}
            />
          </div>

          {/* Search Bar - Wider and Centered */}
          <div className="hidden md:flex flex-1 max-w-2xl mx-12">
            <div className="relative w-full">
              <input
                type="text"
                placeholder="Search your perfect saree..."
                className="w-full px-6 py-3 pr-14 border-2 border-gray-200 rounded-full focus:outline-none focus:ring-2 focus:ring-red-500 focus:border-red-500 bg-gray-50 text-base placeholder-gray-500 transition-all duration-300"
              />
              <button className="absolute right-2 top-1/2 transform -translate-y-1/2 bg-gradient-to-r from-red-600 to-red-700 text-white p-2.5 rounded-full hover:from-red-700 hover:to-red-800 transition-all duration-300 shadow-md">
                <Search className="w-5 h-5" />
              </button>
            </div>
          </div>

          {/* Cart Icon - Larger and Right-aligned */}
          <div className="flex items-center space-x-8">
            <div className="hidden md:flex items-center cursor-pointer hover:text-red-600 transition-colors relative group">
              <ShoppingCart className="w-8 h-8" />
              <div className="ml-3 flex flex-col">
                <span className="text-sm font-semibold text-gray-800 group-hover:text-red-600">Cart</span>
                <span className="text-xs text-gray-500">2 items</span>
              </div>
              <span className="absolute -top-2 -right-2 bg-red-600 text-white rounded-full w-6 h-6 flex items-center justify-center text-xs font-bold shadow-md">2</span>
            </div>

            {/* Mobile Cart */}
            <div className="md:hidden relative cursor-pointer">
              <ShoppingCart className="w-7 h-7 text-gray-700" />
              <span className="absolute -top-2 -right-2 bg-red-600 text-white rounded-full w-5 h-5 flex items-center justify-center text-xs font-bold">2</span>
            </div>

            {/* Mobile Menu Button */}
            <button
              className="md:hidden p-2"
              onClick={() => setIsMenuOpen(!isMenuOpen)}
            >
              {isMenuOpen ? <X className="w-6 h-6" /> : <Menu className="w-6 h-6" />}
            </button>
          </div>
        </div>

        {/* Navigation Menu - Single Line Below Main Row */}
        <nav className="hidden md:block border-t border-gray-100">
          <div className="flex items-center justify-center space-x-8 py-4">
            {[
              'Fabric', 
              'Handloom Sarees', 
              'Office Wear', 
              'Catalogue Sarees', 
              'Featured', 
              'Latest Collections', 
              'Instagram', 
              'Sale', 
              'About Us'
            ].map((item) => (
              <a 
                key={item}
                href="#" 
                className="text-gray-700 hover:text-red-600 transition-colors font-medium text-sm uppercase tracking-wide whitespace-nowrap py-2 px-1 relative group"
              >
                {item}
                <span className="absolute bottom-0 left-0 w-0 h-0.5 bg-red-600 transition-all duration-300 group-hover:w-full"></span>
              </a>
            ))}
          </div>
        </nav>

        {/* Mobile Navigation Menu */}
        <nav className={`${isMenuOpen ? 'block' : 'hidden'} md:hidden border-t border-gray-100`}>
          <div className="py-4 space-y-1">
            {[
              'Fabric', 
              'Handloom Sarees', 
              'Office Wear', 
              'Catalogue Sarees', 
              'Featured', 
              'Latest Collections', 
              'Instagram', 
              'Sale', 
              'About Us'
            ].map((item) => (
              <a 
                key={item}
                href="#" 
                className="block text-gray-700 hover:text-red-600 hover:bg-red-50 transition-colors font-medium text-sm uppercase tracking-wide py-3 px-4 rounded-md"
              >
                {item}
              </a>
            ))}
          </div>
        </nav>

        {/* Mobile Search */}
        <div className="md:hidden pb-4">
          <div className="relative">
            <input
              type="text"
              placeholder="Search your perfect saree..."
              className="w-full px-5 py-3 pr-12 border-2 border-gray-200 rounded-full focus:outline-none focus:ring-2 focus:ring-red-500 focus:border-red-500 bg-gray-50 text-base"
            />
            <button className="absolute right-2 top-1/2 transform -translate-y-1/2 bg-red-600 text-white p-2 rounded-full">
              <Search className="w-4 h-4" />
            </button>
          </div>
        </div>
      </div>
    </header>
  );
};

export default Header;