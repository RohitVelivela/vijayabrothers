import React from 'react';

const HeroBanner: React.FC = () => {
  return (
    <section className="relative w-full h-[85vh] md:h-[90vh] overflow-hidden">
      {/* Full Hero Image Background */}
      <div className="absolute inset-0">
        <img
          src="/main image copy copy.png"
          alt="Traditional Indian woman in green-pink saree at temple with Vijay Brothers logo"
          className="w-full h-full object-cover object-center"
          style={{
            imageRendering: 'crisp-edges',
            WebkitImageRendering: 'crisp-edges',
            msImageRendering: 'crisp-edges'
          }}
          loading="eager"
        />
      </div>

      {/* Subtle Left-Side Gradient Overlay for Text Readability */}
      <div className="absolute inset-0 bg-gradient-to-r from-black/50 via-black/20 to-transparent md:from-black/60 md:via-black/25 md:to-transparent"></div>

      {/* Content Container */}
      <div className="relative z-10 h-full flex items-center">
        <div className="container mx-auto px-6 md:px-8">
          <div className="max-w-2xl">
            {/* Main Headline */}
            <h1 className="font-playfair text-4xl md:text-6xl lg:text-7xl font-bold text-white mb-6 leading-tight tracking-wide"
                style={{
                  textShadow: '2px 2px 4px rgba(0, 0, 0, 0.8), 0 0 8px rgba(0, 0, 0, 0.6)'
                }}>
              Timeless Sarees
              <span className="block text-amber-300 mt-2">
                for Every Occasion
              </span>
            </h1>

            {/* Elegant Divider */}
            <div className="flex items-center mb-6">
              <div className="w-16 h-px bg-gradient-to-r from-amber-400 to-transparent"></div>
              <div className="w-3 h-3 bg-amber-400 rounded-full mx-4 shadow-lg"></div>
              <div className="w-24 h-px bg-gradient-to-r from-amber-400 to-transparent"></div>
            </div>

            {/* Subtext */}
            <p className="font-cinzel text-white text-lg md:text-xl lg:text-2xl mb-10 leading-relaxed opacity-95 max-w-xl"
               style={{
                 textShadow: '1px 1px 2px rgba(0, 0, 0, 0.7)'
               }}>
              Explore handpicked silk and traditional sarees crafted with legacy and love.
            </p>

            {/* Call-to-Action Button */}
            <button className="group relative bg-gradient-to-r from-red-700 to-red-800 hover:from-red-800 hover:to-red-900 text-amber-100 px-10 py-4 md:px-12 md:py-5 rounded-full font-playfair font-bold text-lg md:text-xl transition-all duration-500 shadow-2xl hover:shadow-red-900/50 transform hover:-translate-y-1 overflow-hidden">
              {/* Button glow effect */}
              <div className="absolute inset-0 bg-gradient-to-r from-amber-400/20 to-red-400/20 rounded-full opacity-0 group-hover:opacity-100 transition-opacity duration-500"></div>
              
              {/* Button shimmer effect */}
              <div className="absolute inset-0 bg-gradient-to-r from-transparent via-white/30 to-transparent -translate-x-full group-hover:translate-x-full transition-transform duration-1000 rounded-full"></div>
              
              <span className="relative z-10 tracking-wide">Shop Now</span>
            </button>

            {/* Optional Secondary CTA */}
            <button className="ml-6 font-cinzel text-white border-2 border-white/60 hover:border-amber-300 hover:text-amber-300 px-8 py-4 md:px-10 md:py-5 rounded-full font-semibold text-lg transition-all duration-300 hidden md:inline-block"
                    style={{
                      textShadow: '1px 1px 2px rgba(0, 0, 0, 0.5)'
                    }}>
              View Catalog
            </button>
          </div>
        </div>
      </div>

      {/* Decorative Bottom Border */}
      <div className="absolute bottom-0 left-0 right-0">
        <div className="h-1 bg-gradient-to-r from-amber-500 via-red-500 to-green-500"></div>
        <div className="h-px bg-gradient-to-r from-transparent via-white/40 to-transparent"></div>
      </div>

      {/* Optional Scroll Indicator */}
      <div className="absolute bottom-8 left-1/2 transform -translate-x-1/2 hidden md:block">
        <div className="flex flex-col items-center text-white/70 animate-bounce">
          <span className="font-cinzel text-sm mb-2 tracking-wide">Scroll to Explore</span>
          <svg className="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M19 14l-7 7m0 0l-7-7m7 7V3" />
          </svg>
        </div>
      </div>
    </section>
  );
};

export default HeroBanner;