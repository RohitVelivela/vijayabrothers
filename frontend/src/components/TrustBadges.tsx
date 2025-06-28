import React from 'react';
import { Shield, RotateCcw, Truck, Phone, Star, Heart } from 'lucide-react';

const badges = [
  {
    icon: Shield,
    title: 'Secure Payment',
    description: '100% secure payment gateway with SSL encryption'
  },
  {
    icon: RotateCcw,
    title: 'Easy Returns',
    description: '7-day hassle-free return and exchange policy'
  },
  {
    icon: Truck,
    title: 'Free Shipping',
    description: 'Free shipping on orders above ₹2000'
  },
  {
    icon: Heart,
    title: 'Handpicked Quality',
    description: 'Each saree is personally selected for quality'
  },
  {
    icon: Star,
    title: 'Authentic Products',
    description: '100% authentic sarees from verified sources'
  },
  {
    icon: Phone,
    title: '24/7 Support',
    description: 'Dedicated customer support via WhatsApp'
  }
];

export default function TrustBadges() {
  return (
    <section className="py-16 bg-gradient-to-r from-amber-50 to-orange-50">
      <div className="container mx-auto px-4">
        <div className="text-center mb-12">
          <h2 className="text-3xl md:text-4xl font-bold text-gray-800 mb-4">
            Why Choose Vijay Brothers?
          </h2>
          <p className="text-gray-600 max-w-2xl mx-auto">
            We are committed to providing the finest sarees with exceptional service
          </p>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
          {badges.map((badge, index) => (
            <div
              key={index}
              className="flex items-start space-x-4 p-6 bg-white rounded-xl shadow-md hover:shadow-lg transition-shadow duration-300"
            >
              <div className="flex-shrink-0">
                <div className="w-12 h-12 bg-amber-100 rounded-lg flex items-center justify-center">
                  <badge.icon className="w-6 h-6 text-amber-600" />
                </div>
              </div>
              <div>
                <h3 className="font-semibold text-gray-800 mb-2">{badge.title}</h3>
                <p className="text-gray-600 text-sm">{badge.description}</p>
              </div>
            </div>
          ))}
        </div>
      </div>
    </section>
  );
}