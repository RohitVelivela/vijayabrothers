import React from 'react';
import { Link } from 'react-router-dom';
import { Phone, Mail, MapPin, Facebook, Instagram, Youtube } from 'lucide-react';

export default function Footer() {
  return (
    <footer className="bg-gray-900 text-white">
      <div className="container mx-auto px-4 py-12">
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-8">
          {/* Company Info */}
          <div>
            <div className="flex items-center space-x-2 mb-4">
              <div className="bg-gradient-to-r from-amber-500 to-amber-600 text-white p-2 rounded-lg">
                <span className="font-bold text-xl">VB</span>
              </div>
              <div>
                <h3 className="text-xl font-bold">Vijay Brothers</h3>
                <p className="text-sm text-gray-400">Premium Sarees</p>
              </div>
            </div>
            <p className="text-gray-400 mb-4">
              Your trusted destination for authentic handpicked sarees from Andhra Pradesh and Telangana. 
              Quality and tradition in every thread.
            </p>
            <div className="flex space-x-4">
              <a href="#" className="text-gray-400 hover:text-white">
                <Facebook className="w-5 h-5" />
              </a>
              <a href="#" className="text-gray-400 hover:text-white">
                <Instagram className="w-5 h-5" />
              </a>
              <a href="#" className="text-gray-400 hover:text-white">
                <Youtube className="w-5 h-5" />
              </a>
            </div>
          </div>

          {/* Quick Links */}
          <div>
            <h4 className="font-semibold mb-4">Quick Links</h4>
            <ul className="space-y-2">
              <li><Link to="/about" className="text-gray-400 hover:text-white">About Us</Link></li>
              <li><Link to="/contact" className="text-gray-400 hover:text-white">Contact Us</Link></li>
              <li><Link to="/faq" className="text-gray-400 hover:text-white">FAQ</Link></li>
              <li><Link to="/size-guide" className="text-gray-400 hover:text-white">Size Guide</Link></li>
              <li><Link to="/care-instructions" className="text-gray-400 hover:text-white">Care Instructions</Link></li>
            </ul>
          </div>

          {/* Collections */}
          <div>
            <h4 className="font-semibold mb-4">Collections</h4>
            <ul className="space-y-2">
              <li><Link to="/collections/pattu" className="text-gray-400 hover:text-white">Pattu Sarees</Link></li>
              <li><Link to="/collections/ikkath" className="text-gray-400 hover:text-white">Ikkath Sarees</Link></li>
              <li><Link to="/collections/mangalagiri" className="text-gray-400 hover:text-white">Mangalagiri</Link></li>
              <li><Link to="/collections/linen" className="text-gray-400 hover:text-white">Linen Sarees</Link></li>
              <li><Link to="/collections/bridal" className="text-gray-400 hover:text-white">Bridal Collection</Link></li>
            </ul>
          </div>

          {/* Contact Info */}
          <div>
            <h4 className="font-semibold mb-4">Contact Info</h4>
            <div className="space-y-3">
              <div className="flex items-start space-x-3">
                <MapPin className="w-5 h-5 mt-0.5 text-amber-500 flex-shrink-0" />
                <p className="text-gray-400">
                  123 Saree Street, Hyderabad,<br />
                  Telangana - 500001, India
                </p>
              </div>
              <div className="flex items-center space-x-3">
                <Phone className="w-5 h-5 text-amber-500" />
                <p className="text-gray-400">+91 9876543210</p>
              </div>
              <div className="flex items-center space-x-3">
                <Mail className="w-5 h-5 text-amber-500" />
                <p className="text-gray-400">info@vijaybrothers.in</p>
              </div>
            </div>
          </div>
        </div>

        <div className="border-t border-gray-800 mt-12 pt-8">
          <div className="flex flex-col md:flex-row justify-between items-center">
            <p className="text-gray-400 mb-4 md:mb-0">
              © 2024 Vijay Brothers. All rights reserved.
            </p>
            <div className="flex space-x-6 text-sm">
              <Link to="/privacy" className="text-gray-400 hover:text-white">Privacy Policy</Link>
              <Link to="/terms" className="text-gray-400 hover:text-white">Terms & Conditions</Link>
              <Link to="/shipping" className="text-gray-400 hover:text-white">Shipping Policy</Link>
              <Link to="/refund" className="text-gray-400 hover:text-white">Refund Policy</Link>
            </div>
          </div>
        </div>
      </div>
    </footer>
  );
}