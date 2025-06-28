import React, { useState, useMemo } from 'react';
import { useParams } from 'react-router-dom';
import { Filter, SortAsc, Grid, List } from 'lucide-react';
import ProductCard from '../components/ProductCard';
import { products } from '../data/products';
import { FilterOptions } from '../types';

export default function CollectionPage() {
  const { category } = useParams<{ category: string }>();
  const [showFilters, setShowFilters] = useState(false);
  const [sortBy, setSortBy] = useState('name');
  const [viewMode, setViewMode] = useState<'grid' | 'list'>('grid');
  const [filters, setFilters] = useState<FilterOptions>({
    priceRange: [0, 10000],
    colors: [],
    fabrics: [],
    availability: []
  });

  const filteredAndSortedProducts = useMemo(() => {
    let filtered = products;

    // Filter by category
    if (category && category !== 'all') {
      filtered = filtered.filter(product => 
        product.category.toLowerCase() === category.toLowerCase() ||
        product.collection.toLowerCase() === category.toLowerCase()
      );
    }

    // Apply price filter
    filtered = filtered.filter(product => 
      product.price >= filters.priceRange[0] && product.price <= filters.priceRange[1]
    );

    // Apply fabric filter
    if (filters.fabrics.length > 0) {
      filtered = filtered.filter(product => 
        filters.fabrics.includes(product.fabric)
      );
    }

    // Apply availability filter
    if (filters.availability.length > 0) {
      filtered = filtered.filter(product => 
        filters.availability.includes(product.availability)
      );
    }

    // Sort products
    filtered.sort((a, b) => {
      switch (sortBy) {
        case 'price-low':
          return a.price - b.price;
        case 'price-high':
          return b.price - a.price;
        case 'newest':
          return a.isNew ? -1 : 1;
        default:
          return a.name.localeCompare(b.name);
      }
    });

    return filtered;
  }, [category, filters, sortBy]);

  const categoryTitle = category ? 
    category.charAt(0).toUpperCase() + category.slice(1) + ' Sarees' : 
    'All Sarees';

  const handlePriceRangeChange = (range: [number, number]) => {
    setFilters(prev => ({ ...prev, priceRange: range }));
  };

  const handleFabricToggle = (fabric: string) => {
    setFilters(prev => ({
      ...prev,
      fabrics: prev.fabrics.includes(fabric)
        ? prev.fabrics.filter(f => f !== fabric)
        : [...prev.fabrics, fabric]
    }));
  };

  const handleAvailabilityToggle = (availability: string) => {
    setFilters(prev => ({
      ...prev,
      availability: prev.availability.includes(availability)
        ? prev.availability.filter(a => a !== availability)
        : [...prev.availability, availability]
    }));
  };

  const clearFilters = () => {
    setFilters({
      priceRange: [0, 10000],
      colors: [],
      fabrics: [],
      availability: []
    });
  };

  const uniqueFabrics = [...new Set(products.map(p => p.fabric))];
  const availabilityOptions = ['in-stock', 'limited', 'out-of-stock'];

  return (
    <div className="min-h-screen bg-gray-50">
      <div className="container mx-auto px-4 py-8">
        {/* Header */}
        <div className="mb-8">
          <h1 className="text-3xl md:text-4xl font-bold text-gray-800 mb-4">
            {categoryTitle}
          </h1>
          <p className="text-gray-600">
            {filteredAndSortedProducts.length} products found
          </p>
        </div>

        {/* Filter and Sort Bar */}
        <div className="bg-white rounded-lg shadow-sm p-4 mb-8">
          <div className="flex flex-col md:flex-row md:items-center md:justify-between gap-4">
            <div className="flex items-center gap-4">
              <button
                onClick={() => setShowFilters(!showFilters)}
                className="flex items-center space-x-2 px-4 py-2 border border-gray-300 rounded-lg hover:bg-gray-50"
              >
                <Filter className="w-4 h-4" />
                <span>Filters</span>
              </button>
              
              <select
                value={sortBy}
                onChange={(e) => setSortBy(e.target.value)}
                className="px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-amber-500"
              >
                <option value="name">Sort by Name</option>
                <option value="price-low">Price: Low to High</option>
                <option value="price-high">Price: High to Low</option>
                <option value="newest">Newest First</option>
              </select>
            </div>

            <div className="flex items-center gap-2">
              <button
                onClick={() => setViewMode('grid')}
                className={`p-2 rounded ${viewMode === 'grid' ? 'bg-amber-500 text-white' : 'bg-gray-100'}`}
              >
                <Grid className="w-4 h-4" />
              </button>
              <button
                onClick={() => setViewMode('list')}
                className={`p-2 rounded ${viewMode === 'list' ? 'bg-amber-500 text-white' : 'bg-gray-100'}`}
              >
                <List className="w-4 h-4" />
              </button>
            </div>
          </div>

          {/* Filters Panel */}
          {showFilters && (
            <div className="mt-6 pt-6 border-t border-gray-200">
              <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
                {/* Price Range */}
                <div>
                  <h3 className="font-semibold mb-3">Price Range</h3>
                  <div className="space-y-2">
                    <input
                      type="range"
                      min="0"
                      max="10000"
                      step="500"
                      value={filters.priceRange[1]}
                      onChange={(e) => handlePriceRangeChange([0, parseInt(e.target.value)])}
                      className="w-full"
                    />
                    <div className="flex justify-between text-sm text-gray-600">
                      <span>₹0</span>
                      <span>₹{filters.priceRange[1].toLocaleString()}</span>
                    </div>
                  </div>
                </div>

                {/* Fabric */}
                <div>
                  <h3 className="font-semibold mb-3">Fabric</h3>
                  <div className="space-y-2">
                    {uniqueFabrics.map(fabric => (
                      <label key={fabric} className="flex items-center">
                        <input
                          type="checkbox"
                          checked={filters.fabrics.includes(fabric)}
                          onChange={() => handleFabricToggle(fabric)}
                          className="mr-2"
                        />
                        <span className="text-sm">{fabric}</span>
                      </label>
                    ))}
                  </div>
                </div>

                {/* Availability */}
                <div>
                  <h3 className="font-semibold mb-3">Availability</h3>
                  <div className="space-y-2">
                    {availabilityOptions.map(option => (
                      <label key={option} className="flex items-center">
                        <input
                          type="checkbox"
                          checked={filters.availability.includes(option)}
                          onChange={() => handleAvailabilityToggle(option)}
                          className="mr-2"
                        />
                        <span className="text-sm capitalize">{option.replace('-', ' ')}</span>
                      </label>
                    ))}
                  </div>
                </div>
              </div>
              
              <div className="mt-4">
                <button
                  onClick={clearFilters}
                  className="text-amber-600 hover:text-amber-700 text-sm font-medium"
                >
                  Clear All Filters
                </button>
              </div>
            </div>
          )}
        </div>

        {/* Products Grid */}
        <div className={`grid gap-6 ${
          viewMode === 'grid' 
            ? 'grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4'
            : 'grid-cols-1'
        }`}>
          {filteredAndSortedProducts.map((product) => (
            <ProductCard 
              key={product.id} 
              product={product}
              className={viewMode === 'list' ? 'flex flex-row' : ''}
            />
          ))}
        </div>

        {filteredAndSortedProducts.length === 0 && (
          <div className="text-center py-12">
            <p className="text-gray-500 text-lg">No products found matching your criteria.</p>
            <button
              onClick={clearFilters}
              className="mt-4 px-6 py-2 bg-amber-500 text-white rounded-lg hover:bg-amber-600"
            >
              Clear Filters
            </button>
          </div>
        )}
      </div>
    </div>
  );
}