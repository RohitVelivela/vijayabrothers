import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { Search, ShoppingCart, Heart, User, Menu, X, ChevronDown } from 'lucide-react';
import { useAppContext } from '../context/AppContext';

export default function Header() {
  const [isMenuOpen, setIsMenuOpen] = useState(false);
  const [searchQuery, setSearchQuery] = useState('');
  const { state } = useAppContext();
  const navigate = useNavigate();
  const cartItemsCount = state.cart.reduce((sum, item) => sum + item.quantity, 0);

  const handleSearch = (e: React.FormEvent) => {
    e.preventDefault();
    if (searchQuery.trim()) {
      navigate(`/search?q=${encodeURIComponent(searchQuery)}`);
      setSearchQuery('');
    }
  };

  const navigationItems = [
    { name: 'Fabric', href: '/collections/fabric' },
    { name: 'Handloom Sarees', href: '/collections/handloom' },
    { name: 'Office Wear', href: '/collections/office-wear' },
    { name: 'Catalogue Sarees', href: '/collections/catalogue' },
    { name: 'Featured', href: '/collections/featured' },
    { name: 'Latest Collections', href: '/collections/latest' },
    { name: 'Instagram', href: '/collections/instagram' },
    { name: 'Sale', href: '/collections/sale' },
    { name: 'About Us', href: '/about' }
  ];

  return (
    <header className="bg-white shadow-sm sticky top-0 z-50">
      {/* Hero Banner */}
      <div className="relative w-full bg-[#1a1a1a] overflow-hidden">
        <div className="container mx-auto flex items-center justify-between h-[300px]">
          {/* Left side - Model Image */}
          <div className="relative w-2/3 h-full">
            <img 
              src="/banner.png" 
              alt="Traditional Saree Collection" 
              className="h-full w-full object-cover object-right"
            />
            <div className="absolute inset-0 bg-gradient-to-r from-transparent to-[#1a1a1a] opacity-50"></div>
          </div>
          
          {/* Right side - Logo */}
          <div className="absolute right-0 w-1/3 h-full flex items-center justify-center pr-8">
            <img 
              src="/logo.jpg" 
              alt="Vijay Brothers Logo" 
              className="max-w-[300px] w-full"
            />
          </div>
        </div>
      </div>

      {/* Main Header */}
      <div className="container mx-auto px-4 py-4">
        <div className="flex items-center justify-between">
          {/* Logo */}
          <Link to="/" className="flex items-center space-x-3">
            <img 
              src="/logo.jpg" 
              alt="Vijay Brothers Logo" 
              className="h-16 w-auto object-contain"
            />
            <div className="hidden sm:block">
              <h1 className="text-xl font-bold text-gray-800 tracking-wide">
                VIJAY BROTHERS
              </h1>
              <p className="text-xs text-gray-600 uppercase tracking-wider">
                Premium Sarees Collection
              </p>
            </div>
          </Link>

          {/* Search Bar - Desktop */}
          <form onSubmit={handleSearch} className="hidden md:flex flex-1 max-w-lg mx-8">
            <div className="relative w-full">
              <input
                type="text"
                placeholder="Search your product..."
                value={searchQuery}
                onChange={(e) => setSearchQuery(e.target.value)}
                className="w-full pl-4 pr-12 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-orange-500 focus:border-transparent text-gray-700 placeholder-gray-500"
              />
              <button
                type="submit"
                className="absolute right-3 top-1/2 transform -translate-y-1/2 p-1 text-gray-400 hover:text-orange-600 transition-colors"
              >
                <Search className="w-5 h-5" />
              </button>
            </div>
          </form>

          {/* Right Actions */}
          <div className="flex items-center space-x-6">
            {/* Country Selector */}
            <div className="hidden md:flex items-center space-x-2 cursor-pointer group">
              <div className="w-6 h-4 bg-gradient-to-b from-orange-500 via-white to-green-500 rounded-sm border border-gray-300 flex items-center justify-center relative overflow-hidden">
                <div className="absolute inset-0 bg-gradient-to-b from-orange-500 via-white to-green-500"></div>
                <div className="absolute inset-0 flex items-center justify-center">
                  <div className="w-3 h-3 border border-blue-800 rounded-full bg-blue-800 flex items-center justify-center">
                    <div className="w-2 h-2 bg-white rounded-full"></div>
                  </div>
                </div>
              </div>
              <span className="text-sm font-medium text-gray-700 group-hover:text-orange-600">IN</span>
              <ChevronDown className="w-4 h-4 text-gray-500 group-hover:text-orange-600" />
            </div>

            {/* Wishlist */}
            <Link to="/wishlist" className="flex flex-col items-center group">
              <div className="relative p-2">
                <Heart className="w-6 h-6 text-gray-600 group-hover:text-orange-600 transition-colors" />
                {state.wishlist.length > 0 && (
                  <span className="absolute -top-1 -right-1 bg-orange-500 text-white text-xs rounded-full w-5 h-5 flex items-center justify-center font-medium">
                    {state.wishlist.length}
                  </span>
                )}
              </div>
              <span className="text-xs text-gray-600 group-hover:text-orange-600 transition-colors">Wishlist</span>
            </Link>

            {/* Cart */}
            <Link to="/cart" className="flex flex-col items-center group">
              <div className="relative p-2">
                <ShoppingCart className="w-6 h-6 text-gray-600 group-hover:text-orange-600 transition-colors" />
                {cartItemsCount > 0 && (
                  <span className="absolute -top-1 -right-1 bg-orange-500 text-white text-xs rounded-full w-5 h-5 flex items-center justify-center font-medium">
                    {cartItemsCount}
                  </span>
                )}
              </div>
              <span className="text-xs text-gray-600 group-hover:text-orange-600 transition-colors">Cart</span>
            </Link>

            {/* My Account */}
            <Link to="/account" className="flex flex-col items-center group">
              <div className="p-2">
                <User className="w-6 h-6 text-gray-600 group-hover:text-orange-600 transition-colors" />
              </div>
              <span className="text-xs text-gray-600 group-hover:text-orange-600 transition-colors">My Account</span>
            </Link>

            {/* Mobile Menu Toggle */}
            <button
              onClick={() => setIsMenuOpen(!isMenuOpen)}
              className="md:hidden p-2 text-gray-600 hover:text-orange-600"
            >
              {isMenuOpen ? <X className="w-6 h-6" /> : <Menu className="w-6 h-6" />}
            </button>
          </div>
        </div>

        {/* Navigation - Desktop */}
        <nav className="hidden md:flex justify-center mt-6 space-x-8 border-t border-gray-100 pt-4">
          {navigationItems.map((item) => (
            <Link
              key={item.name}
              to={item.href}
              className="text-gray-700 hover:text-orange-600 font-medium transition-colors duration-200 py-2 px-1 relative group"
            >
              {item.name}
              <span className="absolute bottom-0 left-0 w-0 h-0.5 bg-orange-600 transition-all duration-300 group-hover:w-full"></span>
            </Link>
          ))}
        </nav>
      </div>

      {/* Mobile Menu */}
      {isMenuOpen && (
        <div className="md:hidden bg-white border-t border-gray-200 shadow-lg">
          <div className="px-4 py-4">
            {/* Mobile Search */}
            <form onSubmit={handleSearch} className="mb-4">
              <div className="relative">
                <input
                  type="text"
                  placeholder="Search your product..."
                  value={searchQuery}
                  onChange={(e) => setSearchQuery(e.target.value)}
                  className="w-full pl-4 pr-12 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-orange-500 focus:border-transparent"
                />
                <button
                  type="submit"
                  className="absolute right-3 top-1/2 transform -translate-y-1/2 p-1 text-gray-400"
                >
                  <Search className="w-5 h-5" />
                </button>
              </div>
            </form>

            {/* Mobile Navigation */}
            <nav className="space-y-2">
              {navigationItems.map((item) => (
                <Link
                  key={item.name}
                  to={item.href}
                  className="block py-3 px-2 text-gray-700 hover:text-orange-600 hover:bg-orange-50 rounded-lg transition-colors"
                  onClick={() => setIsMenuOpen(false)}
                >
                  {item.name}
                </Link>
              ))}
            </nav>

            {/* Mobile Country Selector */}
            <div className="mt-4 pt-4 border-t border-gray-200">
              <div className="flex items-center space-x-2 cursor-pointer">
                <div className="w-6 h-4 bg-gradient-to-b from-orange-500 via-white to-green-500 rounded-sm border border-gray-300"></div>
                <span className="text-sm font-medium text-gray-700">India (IN)</span>
                <ChevronDown className="w-4 h-4 text-gray-500" />
              </div>
            </div>

            {/* Mobile Traditional Image */}
            <div className="mt-4 pt-4 border-t border-gray-200 lg:hidden">
              <img 
                src="/main image.png" 
                alt="Traditional Saree Collection" 
                className="w-full h-20 object-cover rounded-lg shadow-md"
              />
            </div>
          </div>
        </div>
      )}
    </header>
  );
}