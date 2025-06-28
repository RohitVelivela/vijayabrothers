import React, { useState, useEffect } from 'react';
import { ChevronLeft, ChevronRight } from 'lucide-react';

const heroSlides = [
  {
    id: 1,
    title: 'Festive Collection 2024',
    subtitle: 'Celebrate with Elegance',
    description: 'Discover our handpicked collection of traditional sarees for every celebration',
    image: 'https://images.pexels.com/photos/8840001/pexels-photo-8840001.jpeg',
    cta: 'Shop Festive Collection',
    offer: 'Up to 40% OFF'
  },
  {
    id: 2,
    title: 'Bridal Luxury Collection',
    subtitle: 'Your Dream Wedding Saree',
    description: 'Exquisite bridal sarees crafted with finest silk and intricate embroidery',
    image: 'https://images.pexels.com/photos/8839999/pexels-photo-8839999.jpeg',
    cta: 'Explore Bridal Collection',
    offer: 'Starting from ₹5,999'
  },
  {
    id: 3,
    title: 'Summer Linen Collection',
    subtitle: 'Cool & Comfortable',
    description: 'Breathable linen sarees perfect for the summer season',
    image: 'https://images.pexels.com/photos/7945632/pexels-photo-7945632.jpeg',
    cta: 'Shop Summer Collection',
    offer: 'Free Shipping'
  }
];

export default function HeroSection() {
  const [currentSlide, setCurrentSlide] = useState(0);

  useEffect(() => {
    const timer = setInterval(() => {
      setCurrentSlide((prev) => (prev + 1) % heroSlides.length);
    }, 5000);
    return () => clearInterval(timer);
  }, []);

  const nextSlide = () => {
    setCurrentSlide((prev) => (prev + 1) % heroSlides.length);
  };

  const prevSlide = () => {
    setCurrentSlide((prev) => (prev - 1 + heroSlides.length) % heroSlides.length);
  };

  return (
    <section className="relative h-[70vh] md:h-[80vh] overflow-hidden">
      {heroSlides.map((slide, index) => (
        <div
          key={slide.id}
          className={`absolute inset-0 transition-transform duration-700 ease-in-out ${
            index === currentSlide ? 'translate-x-0' : 
            index < currentSlide ? '-translate-x-full' : 'translate-x-full'
          }`}
        >
          <div className="relative h-full">
            <img
              src={slide.image}
              alt={slide.title}
              className="w-full h-full object-cover"
            />
            <div className="absolute inset-0 bg-gradient-to-r from-black/70 via-black/30 to-transparent">
              <div className="container mx-auto px-4 h-full flex items-center">
                <div className="max-w-2xl text-white">
                  <div className="bg-amber-500 text-black px-4 py-2 rounded-full text-sm font-semibold mb-4 inline-block">
                    {slide.offer}
                  </div>
                  <h1 className="text-4xl md:text-6xl font-bold mb-4 leading-tight">
                    {slide.title}
                  </h1>
                  <p className="text-xl md:text-2xl mb-4 text-amber-200">
                    {slide.subtitle}
                  </p>
                  <p className="text-lg mb-8 text-gray-200">
                    {slide.description}
                  </p>
                  <button className="bg-amber-500 hover:bg-amber-600 text-black px-8 py-4 rounded-lg font-semibold text-lg transition-colors duration-200 transform hover:scale-105">
                    {slide.cta}
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
      ))}

      {/* Navigation Arrows */}
      <button
        onClick={prevSlide}
        className="absolute left-4 top-1/2 transform -translate-y-1/2 bg-black/50 hover:bg-black/70 text-white p-3 rounded-full transition-colors duration-200"
      >
        <ChevronLeft className="w-6 h-6" />
      </button>
      <button
        onClick={nextSlide}
        className="absolute right-4 top-1/2 transform -translate-y-1/2 bg-black/50 hover:bg-black/70 text-white p-3 rounded-full transition-colors duration-200"
      >
        <ChevronRight className="w-6 h-6" />
      </button>

      {/* Slide Indicators */}
      <div className="absolute bottom-6 left-1/2 transform -translate-x-1/2 flex space-x-3">
        {heroSlides.map((_, index) => (
          <button
            key={index}
            onClick={() => setCurrentSlide(index)}
            className={`w-3 h-3 rounded-full transition-colors duration-200 ${
              index === currentSlide ? 'bg-amber-500' : 'bg-white/50'
            }`}
          />
        ))}
      </div>
    </section>
  );
}