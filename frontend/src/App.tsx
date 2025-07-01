import React, { useState } from 'react';
import Header from './components/Header';
import HeroBanner from './components/HeroBanner';
import CategoryGrid from './components/CategoryGrid';
import ProductGrid from './components/ProductGrid';
import SectionHeader from './components/SectionHeader';
import Footer from './components/Footer';
import ProductDetailPage from './components/ProductDetailPage';
import ShoppingCartPage from './components/ShoppingCartPage';
import AddressPage from './components/AddressPage';
import ShippingPaymentPage from './components/ShippingPaymentPage';
import { featuredCategories } from './data/categories';
import { 
  topHandpickedSarees, 
  newArrivalSarees, 
  festivalCollection, 
  officeWearSarees, 
  casualSarees, 
  premiumCollection 
} from './data/products';

function App() {
  const [currentView, setCurrentView] = useState<'home' | 'product' | 'cart' | 'address' | 'shipping'>('home');

  const handleProductClick = () => {
    setCurrentView('product');
  };

  const handleBackToHome = () => {
    setCurrentView('home');
  };

  const handleBuyNow = () => {
    setCurrentView('cart');
  };

  const handleBackToProduct = () => {
    setCurrentView('product');
  };

  const handleContinueShopping = () => {
    setCurrentView('home');
  };

  const handleCheckOut = () => {
    setCurrentView('address');
  };

  const handleBackToCart = () => {
    setCurrentView('cart');
  };

  const handleNextFromAddress = () => {
    setCurrentView('shipping');
  };

  const handleBackToAddress = () => {
    setCurrentView('address');
  };

  const handleNextFromShipping = () => {
    // This would go to payment processing/completion page
    console.log('Proceeding to payment processing');
  };

  if (currentView === 'shipping') {
    return (
      <ShippingPaymentPage 
        onBack={handleBackToAddress}
        onNext={handleNextFromShipping}
      />
    );
  }

  if (currentView === 'address') {
    return (
      <AddressPage 
        onBack={handleBackToCart}
        onNext={handleNextFromAddress}
      />
    );
  }

  if (currentView === 'cart') {
    return (
      <ShoppingCartPage 
        onBack={handleBackToHome}
        onContinueShopping={handleContinueShopping}
        onCheckOut={handleCheckOut}
      />
    );
  }

  if (currentView === 'product') {
    return (
      <ProductDetailPage 
        onBack={handleBackToHome}
        onBuyNow={handleBuyNow}
      />
    );
  }

  return (
    <div className="min-h-screen bg-white">
      <Header />
      <HeroBanner />
      
      {/* Featured Categories Section */}
      <CategoryGrid 
        categories={featuredCategories} 
        title="Top Categories" 
      />

      {/* Top Handpicked Sarees Section */}
      <section className="py-16">
        <div className="container mx-auto px-4">
          <SectionHeader 
            title="Top Handpicked Sarees" 
            subtitle="Curated collection of our finest sarees"
            showViewAll 
          />
          <div onClick={handleProductClick}>
            <ProductGrid products={topHandpickedSarees} showNavigationArrows />
          </div>
        </div>
      </section>

      {/* New Arrival Sarees Section */}
      <section className="py-16 bg-gray-50">
        <div className="container mx-auto px-4">
          <SectionHeader 
            title="New Arrivals" 
            subtitle="Latest additions to our collection"
            showViewAll 
          />
          <div onClick={handleProductClick}>
            <ProductGrid products={newArrivalSarees} showNavigationArrows />
          </div>
        </div>
      </section>

      {/* Festival Collection Section */}
      <section className="py-16">
        <div className="container mx-auto px-4">
          <SectionHeader 
            title="Festival Collection" 
            subtitle="Perfect sarees for every celebration"
            showViewAll 
          />
          <div onClick={handleProductClick}>
            <ProductGrid products={festivalCollection} showNavigationArrows />
          </div>
        </div>
      </section>

      {/* Office Wear Section */}
      <section className="py-16 bg-gray-50">
        <div className="container mx-auto px-4">
          <SectionHeader 
            title="Office Wear Collection" 
            subtitle="Professional and elegant sarees for work"
            showViewAll 
          />
          <div onClick={handleProductClick}>
            <ProductGrid products={officeWearSarees} showNavigationArrows />
          </div>
        </div>
      </section>

      {/* Casual Sarees Section */}
      <section className="py-16">
        <div className="container mx-auto px-4">
          <SectionHeader 
            title="Casual Collection" 
            subtitle="Comfortable sarees for everyday wear"
            showViewAll 
          />
          <div onClick={handleProductClick}>
            <ProductGrid products={casualSarees} showNavigationArrows />
          </div>
        </div>
      </section>

      {/* Premium Collection Section */}
      <section className="py-16 bg-gradient-to-br from-amber-50 to-orange-50">
        <div className="container mx-auto px-4">
          <SectionHeader 
            title="Premium Luxury Collection" 
            subtitle="Exclusive handcrafted sarees for special occasions"
            showViewAll 
          />
          <div onClick={handleProductClick}>
            <ProductGrid products={premiumCollection} showNavigationArrows />
          </div>
        </div>
      </section>

      <Footer />
    </div>
  );
}

export default App;