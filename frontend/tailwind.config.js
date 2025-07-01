/** @type {import('tailwindcss').Config} */
export default {
  content: ['./index.html', './src/**/*.{js,ts,jsx,tsx}'],
  theme: {
    extend: {
      fontFamily: {
        'playfair': ['Playfair Display', 'serif'],
        'cinzel': ['Cinzel', 'serif'],
      },
      colors: {
        'zari-gold': {
          50: '#fffbeb',
          100: '#fef3c7',
          200: '#fde68a',
          300: '#fcd34d',
          400: '#fbbf24',
          500: '#f59e0b',
          600: '#d97706',
          700: '#b45309',
          800: '#92400e',
          900: '#78350f',
        },
        'silk-ivory': {
          50: '#fefdfb',
          100: '#fef7f0',
          200: '#feeee0',
          300: '#fde0c7',
          400: '#fbcba4',
          500: '#f8b179',
          600: '#f4934c',
          700: '#f0752a',
          800: '#ec5a0a',
          900: '#d14d07',
        }
      },
      boxShadow: {
        'ornate': '0 25px 50px -12px rgba(0, 0, 0, 0.25), inset 0 1px 0 rgba(255, 255, 255, 0.1)',
        'zari': '0 10px 25px -5px rgba(251, 191, 36, 0.3), 0 10px 10px -5px rgba(251, 191, 36, 0.04)',
      },
      backgroundImage: {
        'silk-texture': 'linear-gradient(45deg, #fef7f0 25%, transparent 25%), linear-gradient(-45deg, #fef7f0 25%, transparent 25%), linear-gradient(45deg, transparent 75%, #fef7f0 75%), linear-gradient(-45deg, transparent 75%, #fef7f0 75%)',
      },
      backgroundSize: {
        'silk': '20px 20px',
      }
    },
  },
  plugins: [],
};