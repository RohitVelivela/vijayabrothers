import React, { useState } from 'react';
import { ArrowLeft, Minus, Plus, X, Tag } from 'lucide-react';
import Header from './Header';
import Footer from './Footer';

interface CartItem {
  id: number;
  sku: string;
  title: string;
  price: number;
  image: string;
  quantity: number;
}

interface ShoppingCartPageProps {
  onBack: () => void;
  onContinueShopping: () => void;
  onCheckOut: () => void;
}

const ShoppingCartPage: React.FC<ShoppingCartPageProps> = ({ onBack, onContinueShopping, onCheckOut }) => {
  const [cartItems, setCartItems] = useState<CartItem[]>([
    {
      id: 1,
      sku: 'RKC58307',
      title: 'Tissue Kota Printed Border And Waves ...',
      price: 850,
      image: 'https://images.pexels.com/photos/8839827/pexels-photo-8839827.jpeg?auto=compress&cs=tinysrgb&w=400',
      quantity: 1
    },
    {
      id: 2,
      sku: 'RKIG2326',
      title: 'Fancy Pattu Kanchi Border Purple Saree',
      price: 1480,
      image: 'https://images.pexels.com/photos/8839833/pexels-photo-8839833.jpeg?auto=compress&cs=tinysrgb&w=400',
      quantity: 1
    }
  ]);

  const [couponCode, setCouponCode] = useState('');
  const [currentStep, setCurrentStep] = useState(1);

  const updateQuantity = (id: number, change: number) => {
    setCartItems(items =>
      items.map(item =>
        item.id === id
          ? { ...item, quantity: Math.max(1, item.quantity + change) }
          : item
      )
    );
  };

  const removeItem = (id: number) => {
    setCartItems(items => items.filter(item => item.id !== id));
  };

  const moveToWishlist = (id: number) => {
    // Handle move to wishlist logic
    console.log('Move to wishlist:', id);
  };

  const totalMRP = cartItems.reduce((sum, item) => sum + (item.price * item.quantity), 0);
  const totalAmount = totalMRP; // No discounts applied in this example

  const steps = [
    { number: 1, title: 'Shopping Cart', active: currentStep >= 1 },
    { number: 2, title: 'Address', active: currentStep >= 2 },
    { number: 3, title: 'Shipping & Payment Options', active: currentStep >= 3 },
    { number: 4, title: 'Complete', active: currentStep >= 4 }
  ];

  return (
    <div className="min-h-screen bg-gray-50">
      <Header />
      
      {/* Breadcrumb */}
      <div className="bg-white border-b">
        <div className="container mx-auto px-4 py-3">
          <div className="flex items-center space-x-2 text-sm text-gray-600">
            <button 
              onClick={onBack}
              className="hover:text-gray-800 transition-colors"
            >
              Home
            </button>
            <span>›</span>
            <span className="text-gray-800">Shopping Cart</span>
          </div>
        </div>
      </div>

      {/* Progress Steps */}
      <div className="bg-white border-b">
        <div className="container mx-auto px-4 py-6">
          <div className="flex items-center justify-between max-w-4xl mx-auto">
            {steps.map((step, index) => (
              <div key={step.number} className="flex items-center">
                <div className="flex items-center">
                  <div className={`w-8 h-8 rounded-full flex items-center justify-center text-sm font-semibold ${
                    step.active 
                      ? step.number === currentStep 
                        ? 'bg-orange-500 text-white' 
                        : 'bg-gray-400 text-white'
                      : 'bg-gray-200 text-gray-500'
                  }`}>
                    {step.number}
                  </div>
                  <span className={`ml-3 text-sm font-medium ${
                    step.active ? 'text-gray-800' : 'text-gray-400'
                  }`}>
                    {step.title}
                  </span>
                </div>
                {index < steps.length - 1 && (
                  <div className={`w-16 h-px mx-4 ${
                    step.active ? 'bg-gray-300' : 'bg-gray-200'
                  }`} />
                )}
              </div>
            ))}
          </div>
        </div>
      </div>

      <div className="container mx-auto px-4 py-8">
        <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
          {/* Shopping Cart Items */}
          <div className="lg:col-span-2">
            <div className="bg-white rounded-lg shadow-sm">
              <div className="p-6 border-b">
                <h2 className="text-xl font-semibold text-gray-800">
                  Shopping Cart <span className="text-gray-500 font-normal">(Items {cartItems.length})</span>
                </h2>
              </div>

              {/* Cart Items Header */}
              <div className="px-6 py-4 border-b bg-gray-50">
                <div className="grid grid-cols-12 gap-4 text-sm font-medium text-gray-600 uppercase tracking-wide">
                  <div className="col-span-6">Product Details</div>
                  <div className="col-span-2 text-center">Quantity</div>
                  <div className="col-span-2 text-center">Total</div>
                  <div className="col-span-2"></div>
                </div>
              </div>

              {/* Cart Items */}
              <div className="divide-y">
                {cartItems.map((item) => (
                  <div key={item.id} className="p-6">
                    <div className="grid grid-cols-12 gap-4 items-center">
                      {/* Product Details */}
                      <div className="col-span-6 flex items-center space-x-4">
                        <img
                          src={item.image}
                          alt={item.title}
                          className="w-20 h-24 object-cover rounded-lg"
                        />
                        <div>
                          <p className="text-xs text-gray-500 mb-1">SKU: {item.sku}</p>
                          <h3 className="font-medium text-gray-800 mb-2">{item.title}</h3>
                          <p className="text-lg font-semibold text-gray-900">₹{item.price}</p>
                          <button
                            onClick={() => moveToWishlist(item.id)}
                            className="text-sm text-blue-600 hover:text-blue-800 mt-2 transition-colors"
                          >
                            MOVE TO WISHLIST
                          </button>
                        </div>
                      </div>

                      {/* Quantity */}
                      <div className="col-span-2 flex justify-center">
                        <div className="flex items-center border border-gray-300 rounded-lg">
                          <button
                            onClick={() => updateQuantity(item.id, -1)}
                            className="p-2 hover:bg-gray-50 transition-colors"
                          >
                            <Minus className="w-4 h-4" />
                          </button>
                          <span className="px-4 py-2 font-medium">{item.quantity}</span>
                          <button
                            onClick={() => updateQuantity(item.id, 1)}
                            className="p-2 hover:bg-gray-50 transition-colors"
                          >
                            <Plus className="w-4 h-4" />
                          </button>
                        </div>
                      </div>

                      {/* Total */}
                      <div className="col-span-2 text-center">
                        <p className="text-lg font-semibold text-gray-900">
                          ₹{(item.price * item.quantity).toLocaleString()}
                        </p>
                      </div>

                      {/* Remove */}
                      <div className="col-span-2 flex justify-end">
                        <button
                          onClick={() => removeItem(item.id)}
                          className="p-2 text-gray-400 hover:text-red-500 transition-colors"
                        >
                          <X className="w-5 h-5" />
                        </button>
                      </div>
                    </div>
                  </div>
                ))}
              </div>
            </div>

            {/* Continue Shopping */}
            <div className="mt-6">
              <button
                onClick={onContinueShopping}
                className="flex items-center space-x-2 text-orange-600 hover:text-orange-700 transition-colors"
              >
                <ArrowLeft className="w-4 h-4" />
                <span>Continue Shopping</span>
              </button>
            </div>
          </div>

          {/* Order Summary Sidebar */}
          <div className="space-y-6">
            {/* Coupons */}
            <div className="bg-white rounded-lg shadow-sm p-6">
              <div className="flex items-center space-x-2 mb-4">
                <Tag className="w-5 h-5 text-gray-600" />
                <h3 className="font-semibold text-gray-800">Coupons</h3>
              </div>
              <p className="text-sm text-gray-600 mb-4">
                You don't have any coupon? <button className="text-orange-600 hover:text-orange-700">Check</button>
              </p>
              <div className="flex space-x-2">
                <input
                  type="text"
                  placeholder="Enter coupon code"
                  value={couponCode}
                  onChange={(e) => setCouponCode(e.target.value)}
                  className="flex-1 px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-orange-500 focus:border-orange-500"
                />
                <button className="px-4 py-2 bg-orange-500 text-white rounded-lg hover:bg-orange-600 transition-colors">
                  Apply
                </button>
              </div>
            </div>

            {/* Order Summary */}
            <div className="bg-white rounded-lg shadow-sm p-6">
              <h3 className="font-semibold text-gray-800 mb-4">
                Order Summary <span className="text-gray-500 font-normal">(Items {cartItems.length})</span>
              </h3>
              
              <div className="space-y-3 mb-6">
                <div className="flex justify-between">
                  <span className="text-gray-600">Total MRP</span>
                  <span className="font-semibold">₹{totalMRP.toLocaleString()}</span>
                </div>
                <div className="border-t pt-3">
                  <div className="flex justify-between text-lg font-semibold">
                    <span>Total</span>
                    <span>₹{totalAmount.toLocaleString()}</span>
                  </div>
                </div>
              </div>

              <button 
                onClick={onCheckOut}
                className="w-full bg-orange-500 hover:bg-orange-600 text-white py-3 rounded-lg font-semibold transition-colors"
              >
                Check Out
              </button>
            </div>
          </div>
        </div>
      </div>

      <Footer />
    </div>
  );
};

export default ShoppingCartPage;